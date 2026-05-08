package model;

public class Instrutor extends Pessoa {
    private String especialidade;
    private String horariosTrabalho;
    private double salario; // Adicionado para satisfazer calcularCustoMensal

    public Instrutor(int id, String nome, String cpf, String telefone, String especialidade, String horariosTrabalho) {
        super(id, nome, cpf, telefone);
        this.especialidade = especialidade;
        this.horariosTrabalho = horariosTrabalho;
        this.salario = 2500.0; // Valor padrão para exemplo
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Instrutor ---");
        exibirInformacoesBasicas();
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Horários de Trabalho: " + horariosTrabalho);
        System.out.println("Salário: R$ " + salario);
    }

    @Override
    public double calcularCustoMensal() {
        return salario;
    }

    // Getters e Setters
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getHorariosTrabalho() { return horariosTrabalho; }
    public void setHorariosTrabalho(String horariosTrabalho) { this.horariosTrabalho = horariosTrabalho; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}