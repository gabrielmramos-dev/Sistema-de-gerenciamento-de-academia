package model;

/**
 * Representa um Funcionário administrativo da academia.
 * Estende {@link Pessoa} — requisito CP2: herança.
 */
public class Funcionario extends Pessoa {
    private String cargo;
    private String turno;
    private double salario;

    public Funcionario(int id, String nome, String cpf, String telefone,
                       String cargo, String turno, double salario) {
        super(id, nome, cpf, telefone);
        this.cargo = cargo;
        this.turno = turno;
        this.salario = salario;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Funcionário ---");
        exibirInformacoesBasicas();
        System.out.println("Cargo: " + cargo);
        System.out.println("Turno: " + turno);
        System.out.printf("Salário: R$ %.2f%n", salario);
    }

    @Override
    public double calcularCustoMensal() {
        return salario;
    }

    // Getters e Setters
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}
