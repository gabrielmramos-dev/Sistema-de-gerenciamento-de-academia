package service;

import dao.AlunoDAO;
import dao.AulaDAO;
import dao.InscricaoDAO;
import model.Aluno;
import model.Aula;

/**
 * InscricaoService — Regra de Negócio Complexa (Requisito CP4 - Academia)
 *
 * Centraliza e encapsula toda a lógica de validação para inscrição de alunos em
 * aulas:
 * 1. Plano do aluno deve estar ativo (não vencido)
 * 2. A aula deve existir e ter vagas disponíveis
 * 3. O aluno não pode ter conflito de horário com outra aula já inscrita
 *
 * Segue o padrão Service Layer: a lógica de negócio fica aqui, e o DAO
 * é responsável apenas pela persistência.
 */
public class InscricaoService {

    private final AlunoDAO alunoDAO;
    private final AulaDAO aulaDAO;
    private final InscricaoDAO inscricaoDAO;

    public InscricaoService() {
        this.alunoDAO = new AlunoDAO();
        this.aulaDAO = new AulaDAO();
        this.inscricaoDAO = new InscricaoDAO();
    }

    /**
     * Executa todas as validações e, se aprovado, persiste a inscrição.
     *
     * @param idAluno ID do aluno a ser inscrito
     * @param idAula  ID da aula alvo
     * @return Mensagem de resultado (sucesso ou motivo da rejeição)
     */
    public String inscreverAluno(int idAluno, int idAula) {

        // VALIDAÇÃO 1: O aluno existe no banco?
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        if (aluno == null) {
            return "[ERRO] Aluno nao encontrado (ID " + idAluno + ").";
        }

        // VALIDAÇÃO 2: O plano do aluno está ativo (dentro do prazo)?
        if (!aluno.isPlanoAtivo()) {
            return "[ERRO] O plano do aluno \"" + aluno.getNome() + "\" esta vencido "
                    + "(vencimento: " + aluno.getDataVencimentoPlano() + "). "
                    + "Renove o plano para inscrever-se em aulas.";
        }

        // VALIDAÇÃO 3: A aula existe no banco?
        Aula aula = aulaDAO.buscarPorId(idAula);
        if (aula == null) {
            return "[ERRO] Aula nao encontrada (ID " + idAula + ").";
        }

        // VALIDAÇÃO 4: Ainda há vagas disponíveis?
        int inscritos = inscricaoDAO.contarInscritosNaAula(idAula);
        if (inscritos >= aula.getCapacidadeMaxima()) {
            return "[ERRO] A aula \"" + aula.getNome() + "\" atingiu a capacidade maxima "
                    + "(" + inscritos + "/" + aula.getCapacidadeMaxima() + " alunos).";
        }

        // VALIDAÇÃO 5: Conflito de horário com outra aula do mesmo aluno?
        Aula conflito = inscricaoDAO.buscarConflitoHorario(idAluno, aula.getHorario());
        if (conflito != null) {
            return "[ERRO] Conflito de horario! O aluno ja esta inscrito na aula \""
                    + conflito.getNome() + "\" no mesmo horario (" + conflito.getHorario() + ").";
        }

        // Todas as validações passaram — persiste a inscrição
        boolean sucesso = inscricaoDAO.salvarInscricao(idAluno, idAula);
        if (sucesso) {
            // Registra auditoria na entidade Aluno (interface Auditavel)
            aluno.registrarLog("INSCRICAO na aula \"" + aula.getNome() + "\" [ID " + idAula + "]");
            return "[OK] Sucesso: " + aluno.getNome() + " inscrito(a) na aula \""
                    + aula.getNome() + "\" ("
                    + (inscritos + 1) + "/" + aula.getCapacidadeMaxima() + " vagas ocupadas).";
        }
        return "[ERRO] Erro inesperado ao persistir inscricao no banco. Tente novamente.";
    }

    /**
     * Cancela a inscrição de um aluno em uma aula, validando a existência de ambos.
     *
     * @param idAluno ID do aluno
     * @param idAula  ID da aula
     * @return Mensagem de resultado
     */
    public String cancelarInscricao(int idAluno, int idAula) {
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        Aula aula = aulaDAO.buscarPorId(idAula);

        if (aluno == null)
            return "[ERRO] Aluno nao encontrado.";
        if (aula == null)
            return "[ERRO] Aula nao encontrada.";

        boolean sucesso = inscricaoDAO.cancelarInscricao(idAluno, idAula);
        if (sucesso) {
            aluno.registrarLog("CANCELAMENTO de inscricao na aula \"" + aula.getNome() + "\" [ID " + idAula + "]");
            return "[OK] Inscricao de " + aluno.getNome() + " na aula \"" + aula.getNome()
                    + "\" cancelada com sucesso.";
        }
        return "[ERRO] Inscricao nao encontrada ou erro ao cancelar.";
    }
}
