package model;
import java.time.LocalDate;

public class Aluno extends Pessoa {
    private String email;
    private LocalDate dataNascimento;
    private LocalDate dataMatricula;
    private Plano plano;

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
        System.out.println("Status do Plano: " + (isPlanoAtivo() ? "Ativo" : "Vencido"));
        System.out.println("Data de Vencimento: " + getDataVencimentoPlano());
    }

    @Override
    public double calcularCustoMensal() {
        return 0; // Alunos não representam custo, mas sim receita
    }

    public LocalDate getDataVencimentoPlano() {
        if (plano == null) return dataMatricula;
        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    public boolean isPlanoAtivo() {
        return LocalDate.now().isBefore(getDataVencimentoPlano()) || LocalDate.now().isEqual(getDataVencimentoPlano());
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