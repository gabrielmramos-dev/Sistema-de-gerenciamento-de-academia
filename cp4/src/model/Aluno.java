package model;

import util.Auditavel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Aluno matriculado na academia.
 *
 * Implementa {@link Auditavel} — requisito CP4: interface de auditoria.
 * Estende {@link Pessoa} — requisito CP2/CP3: herança.
 */
public class Aluno extends Pessoa implements Auditavel {
    private String email;
    private LocalDate dataNascimento;
    private LocalDate dataMatricula;
    private Plano plano;

    /** Histórico de ações auditadas nesta entidade. */
    private final List<String> historico = new ArrayList<>();

    public Aluno(int id, String nome, String cpf, String telefone, String email,
                 LocalDate dataNascimento, LocalDate dataMatricula, Plano plano) {
        super(id, nome, cpf, telefone);
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataMatricula = dataMatricula;
        this.plano = plano;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Aluno ---");
        exibirInformacoesBasicas();
        System.out.println("Email: " + email);
        System.out.println("Data de Matrícula: " + dataMatricula);
        System.out.println("Plano: " + (plano != null ? plano.getNome() : "Nenhum"));
        System.out.println("Status do Plano: " + (isPlanoAtivo() ? "[Ativo]" : "[Vencido]"));
        System.out.println("Data de Vencimento: " + getDataVencimentoPlano());
    }

    @Override
    public double calcularCustoMensal() {
        // Alunos representam receita; custo mensal é zero para a entidade Aluno
        return 0;
    }

    /** Calcula a data de vencimento com base na matrícula e duração do plano. */
    public LocalDate getDataVencimentoPlano() {
        if (plano == null) return dataMatricula;
        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    /** Verifica se o plano ainda está dentro da validade. */
    public boolean isPlanoAtivo() {
        LocalDate vencimento = getDataVencimentoPlano();
        return !LocalDate.now().isAfter(vencimento);
    }

    // --- Implementação da interface Auditavel ---

    @Override
    public void registrarLog(String acao) {
        String entrada = LocalDateTime.now() + " | ALUNO[" + id + "] | " + acao;
        historico.add(entrada);
        System.out.println("📝 Log: " + entrada);
    }

    @Override
    public String obterHistorico() {
        if (historico.isEmpty()) return "Nenhum registro de auditoria para este aluno.";
        StringBuilder sb = new StringBuilder("=== HISTÓRICO DO ALUNO " + nome + " ===\n");
        historico.forEach(log -> sb.append("  ").append(log).append("\n"));
        return sb.toString();
    }

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }
    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }
}
