/**
 * Superclasse que representa uma pessoa no sistema da academia.
 * Contém atributos e comportamentos comuns a todos os tipos de pessoa.
 */
public class Pessoa {
    protected int id;
    protected String nome;
    protected String cpf;
    protected String telefone;

    public Pessoa(int id, String nome, String cpf, String telefone) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo");
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF não pode ser vazio");
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty())
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        this.telefone = telefone;
    }

    /**
     * Exibe informações básicas da pessoa.
     * Subclasses devem sobrescrever este método para adicionar dados específicos.
     */
    public void exibirInfo() {
        System.out.println("=== INFORMAÇÕES DA PESSOA ===");
        System.out.println("ID      : " + id);
        System.out.println("Nome    : " + nome);
        System.out.println("CPF     : " + cpf);
        System.out.println("Telefone: " + telefone);
    }

    /**
     * Calcula o custo mensal associado a esta pessoa.
     * Cada subclasse define sua própria regra de cálculo.
     */
    public double calcularCustoMensal() {
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Pessoa[ID=%d, Nome=%s, CPF=%s, Tel=%s]",
                id, nome, cpf, telefone);
    }
}
