/**
 * Subclasse de Pessoa que representa um funcionário administrativo da academia.
 * Terceira subclasse da hierarquia, completando o requisito do CP2.
 */
public class Funcionario extends Pessoa {
    private String cargo;
    private String turno;
    private double salario;

    public Funcionario(int id, String nome, String cpf, String telefone,
                       String cargo, String turno, double salario) {
        super(id, nome, cpf, telefone); // chama construtor da superclasse
        setCargo(cargo);
        setTurno(turno);
        setSalario(salario);
    }

    public String getCargo() { return cargo; }
    public String getTurno() { return turno; }
    public double getSalario() { return salario; }

    public void setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty())
            throw new IllegalArgumentException("Cargo não pode ser vazio");
        this.cargo = cargo;
    }

    public void setTurno(String turno) {
        if (turno == null || turno.trim().isEmpty())
            throw new IllegalArgumentException("Turno não pode ser vazio");
        this.turno = turno;
    }

    public void setSalario(double salario) {
        if (salario < 0)
            throw new IllegalArgumentException("Salário não pode ser negativo");
        this.salario = salario;
    }

    // =========================================================
    // SOBRESCRITA (Override) - Requisito CP2
    // =========================================================

    /**
     * Custo mensal do funcionário é o seu salário.
     */
    @Override
    public double calcularCustoMensal() {
        return salario;
    }

    /**
     * Exibe informações completas do funcionário,
     * reaproveitando a exibição base de Pessoa.
     */
    @Override
    public void exibirInfo() {
        super.exibirInfo(); // chama exibição base de Pessoa
        System.out.println("Cargo  : " + cargo);
        System.out.println("Turno  : " + turno);
        System.out.println("Salário: R$" + String.format("%.2f", salario));
    }

    @Override
    public String toString() {
        return String.format(
            "Funcionario[ID=%d, Nome=%s, CPF=%s, Tel=%s, Cargo=%s, Turno=%s, Salário=R$%.2f]",
            id, nome, cpf, telefone, cargo, turno, salario
        );
    }
}
