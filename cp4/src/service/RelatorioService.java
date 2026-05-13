package service;

import dao.AlunoDAO;
import dao.AulaDAO;
import dao.FrequenciaDAO;
import dao.InscricaoDAO;
import dao.InstrutorDAO;
import model.Aluno;
import model.Aula;
import model.Instrutor;
import util.Relatorio;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * RelatorioService — implementa a interface {@link Relatorio} (Requisito CP4).
 *
 * Gera relatórios consolidados do sistema de academia:
 *  - Resumo geral de alunos e planos
 *  - Aulas com ocupação de vagas
 *  - Relatório de instrutores
 */
public class RelatorioService implements Relatorio {

    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final AulaDAO aulaDAO = new AulaDAO();
    private final InstrutorDAO instrutorDAO = new InstrutorDAO();
    private final InscricaoDAO inscricaoDAO = new InscricaoDAO();
    private final FrequenciaDAO frequenciaDAO = new FrequenciaDAO();

    /** Constrói o relatório completo como String. */
    private String construirRelatorio() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("       RELATORIO DO SISTEMA DE ACADEMIA           \n");
        sb.append("==================================================\n");
        sb.append("Gerado em: ").append(LocalDateTime.now().format(fmt)).append("\n\n");

        // --- Alunos ---
        List<Aluno> alunos = alunoDAO.listarTodos();
        sb.append("--- ALUNOS (").append(alunos.size()).append(" cadastrados) ---\n");
        long ativos = alunos.stream().filter(Aluno::isPlanoAtivo).count();
        long vencidos = alunos.size() - ativos;
        sb.append(String.format("  [OK] Planos ativos:   %d%n", ativos));
        sb.append(String.format("  [X]  Planos vencidos: %d%n", vencidos));

        for (Aluno a : alunos) {
            int visitas = frequenciaDAO.contarVisitas(a.getId());
            int aulasInscritas = inscricaoDAO.listarAulasPorAluno(a.getId()).size();
            sb.append(String.format("  [%d] %-25s | Plano: %-15s | Status: %-8s | Visitas: %d | Aulas: %d%n",
                a.getId(), a.getNome(),
                (a.getPlano() != null ? a.getPlano().getNome() : "N/A"),
                (a.isPlanoAtivo() ? "Ativo" : "Vencido"),
                visitas, aulasInscritas));
        }

        // --- Aulas ---
        List<Aula> aulas = aulaDAO.listarTodas();
        sb.append("\n--- AULAS (").append(aulas.size()).append(" cadastradas) ---\n");
        for (Aula a : aulas) {
            int inscritos = inscricaoDAO.contarInscritosNaAula(a.getId());
            double ocupacao = a.getCapacidadeMaxima() > 0
                ? (inscritos * 100.0 / a.getCapacidadeMaxima()) : 0;
            sb.append(String.format("  [%d] %-20s | %s | Vagas: %d/%d (%.0f%%) | Instrutor: %s%n",
                a.getId(), a.getNome(),
                a.getHorario().format(fmt),
                inscritos, a.getCapacidadeMaxima(), ocupacao,
                (a.getInstrutor() != null ? a.getInstrutor().getNome() : "N/A")));
        }

        // --- Instrutores ---
        List<Instrutor> instrutores = instrutorDAO.listarTodos();
        sb.append("\n--- INSTRUTORES (").append(instrutores.size()).append(" cadastrados) ---\n");
        for (Instrutor i : instrutores) {
            sb.append(String.format("  [%d] %-25s | Especialidade: %s%n",
                i.getId(), i.getNome(), i.getEspecialidade()));
        }

        sb.append("\n==================================================\n");
        return sb.toString();
    }

    /** Exibe o relatório completo no console. */
    @Override
    public void gerarRelatorio() {
        System.out.println(construirRelatorio());
    }

    /**
     * Exporta o relatório para um arquivo de texto.
     *
     * @param nomeArquivo Nome do arquivo (ex: "relatorio.txt")
     */
    @Override
    public void exportarParaArquivo(String nomeArquivo) {
        String conteudo = construirRelatorio();
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.write(conteudo);
            System.out.println("[OK] Relatorio exportado para: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("[ERRO] Erro ao exportar relatorio: " + e.getMessage());
        }
    }
}
