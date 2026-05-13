package model;

import util.Auditavel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Instrutor da academia.
 *
 * Implementa {@link Auditavel} — requisito CP4: interface de auditoria.
 * Estende {@link Pessoa} — requisito CP2/CP3: herança.
 */
public class Instrutor extends Pessoa implements Auditavel {
    private String especialidade;
    private String horariosTrabalho;
    private double salario;

    /** Histórico de ações auditadas nesta entidade. */
    private final List<String> historico = new ArrayList<>();

    public Instrutor(int id, String nome, String cpf, String telefone, String especialidade, String horariosTrabalho) {
        super(id, nome, cpf, telefone);
        this.especialidade = especialidade;
        this.horariosTrabalho = horariosTrabalho;
        this.salario = 2500.0;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Instrutor ---");
        exibirInformacoesBasicas();
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Horários de Trabalho: " + horariosTrabalho);
        System.out.printf("Salário: R$ %.2f%n", salario);
    }

    @Override
    public double calcularCustoMensal() {
        return salario;
    }

    // --- Implementação da interface Auditavel ---

    @Override
    public void registrarLog(String acao) {
        String entrada = LocalDateTime.now() + " | INSTRUTOR[" + id + "] | " + acao;
        historico.add(entrada);
        System.out.println("📝 Log: " + entrada);
    }

    @Override
    public String obterHistorico() {
        if (historico.isEmpty()) return "Nenhum registro de auditoria para este instrutor.";
        StringBuilder sb = new StringBuilder("=== HISTÓRICO DO INSTRUTOR " + nome + " ===\n");
        historico.forEach(log -> sb.append("  ").append(log).append("\n"));
        return sb.toString();
    }

    // Getters e Setters
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getHorariosTrabalho() { return horariosTrabalho; }
    public void setHorariosTrabalho(String horariosTrabalho) { this.horariosTrabalho = horariosTrabalho; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}
