package model;

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

    // Método Abstrato - Requisito CP3
    public abstract void exibirDetalhes();

    // Método Abstrato - Requisito CP2 (Manutenção de código)
    public abstract double calcularCustoMensal();

    // Método Concreto - Requisito CP3
    public void exibirInformacoesBasicas() {
        System.out.println("ID: " + id + " | Nome: " + nome + " | CPF: " + cpf);
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
