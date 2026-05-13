package model;

/**
 * Superclasse abstrata que representa uma Pessoa no sistema da academia.
 * Centraliza atributos e comportamentos comuns a Aluno, Instrutor e Funcionario.
 *
 * Requisito CP2: Herança e Polimorfismo
 * Requisito CP3: Classe Abstrata com métodos abstratos e concretos
 */
public abstract class Pessoa {
    protected int id;
    protected String nome;
    protected String cpf;
    protected String telefone;

    public Pessoa(int id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    /** Método abstrato — cada subclasse exibe suas informações específicas. */
    public abstract void exibirDetalhes();

    /** Método abstrato — cada subclasse define seu custo mensal. */
    public abstract double calcularCustoMensal();

    /** Método concreto — exibe os atributos básicos herdados. */
    public void exibirInformacoesBasicas() {
        System.out.println("ID: " + id + " | Nome: " + nome + " | CPF: " + cpf + " | Tel: " + telefone);
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
