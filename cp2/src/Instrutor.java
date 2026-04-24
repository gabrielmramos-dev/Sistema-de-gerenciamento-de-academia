/**
 * Subclasse de Pessoa que representa um instrutor da academia.
 * Mantém todos os atributos do CP1, agora herdando de Pessoa.
 */
public class Instrutor extends Pessoa {
    private String especialidade;
    private String horariosTrabalho;
    private double salarioBase;

    public Instrutor(int id, String nome, String cpf, String telefone,
                     String especialidade, String horariosTrabalho) {
        super(id, nome, cpf, telefone); // chama construtor da superclasse
        setEspecialidade(especialidade);
        setHorariosTrabalho(horariosTrabalho);
        this.salarioBase = 2500.00; // salário padrão
    }

    public Instrutor(int id, String nome, String cpf, String telefone,
                     String especialidade, String horariosTrabalho, double salarioBase) {
        super(id, nome, cpf, telefone);
        setEspecialidade(especialidade);
        setHorariosTrabalho(horariosTrabalho);
        setSalarioBase(salarioBase);
    }

    public String getEspecialidade() { return especialidade; }
    public String getHorariosTrabalho() { return horariosTrabalho; }
    public double getSalarioBase() { return salarioBase; }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty())
            throw new IllegalArgumentException("Especialidade não pode ser vazia");
        this.especialidade = especialidade;
    }

    public void setHorariosTrabalho(String horariosTrabalho) {
        if (horariosTrabalho == null || horariosTrabalho.trim().isEmpty())
            throw new IllegalArgumentException("Horários de trabalho não podem ser vazios");
        this.horariosTrabalho = horariosTrabalho;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0)
            throw new IllegalArgumentException("Salário não pode ser negativo");
        this.salarioBase = salarioBase;
    }

    // =========================================================
    // SOBRESCRITA (Override) - Requisito CP2
    // =========================================================

    /**
     * Custo mensal do instrutor é o seu salário base.
     */
    @Override
    public double calcularCustoMensal() {
        return salarioBase;
    }

    /**
     * Exibe informações completas do instrutor,
     * reaproveitando a exibição base de Pessoa.
     */
    @Override
    public void exibirInfo() {
        super.exibirInfo(); // chama exibição base de Pessoa
        System.out.println("Especialidade : " + especialidade);
        System.out.println("Horários      : " + horariosTrabalho);
        System.out.println("Salário Base  : R$" + String.format("%.2f", salarioBase));
    }

    @Override
    public String toString() {
        return String.format(
            "Instrutor[ID=%d, Nome=%s, CPF=%s, Tel=%s, Especialidade=%s, Horários=%s]",
            id, nome, cpf, telefone, especialidade, horariosTrabalho
        );
    }
}
