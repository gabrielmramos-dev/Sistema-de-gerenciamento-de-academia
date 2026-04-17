public class Plano {
    private int id;
    private String nome;
    private String descricao;
    private double valorMensal;
    private int duracaoMeses;
    private String beneficios;

    public Plano(int id, String nome, String descricao, double valorMensal, int duracaoMeses, String beneficios) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
        setValorMensal(valorMensal);
        setDuracaoMeses(duracaoMeses);
        setBeneficios(beneficios);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getValorMensal() { return valorMensal; }
    public int getDuracaoMeses() { return duracaoMeses; }
    public String getBeneficios() { return beneficios; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo");
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) throw new IllegalArgumentException("Descrição não pode ser vazia");
        this.descricao = descricao;
    }

    public void setValorMensal(double valorMensal) {
        if (valorMensal < 0) throw new IllegalArgumentException("Valor mensal não pode ser negativo");
        this.valorMensal = valorMensal;
    }

    public void setDuracaoMeses(int duracaoMeses) {
        if (duracaoMeses <= 0) throw new IllegalArgumentException("Duração deve ser de pelo menos 1 mês");
        this.duracaoMeses = duracaoMeses;
    }

    public void setBeneficios(String beneficios) {
        if (beneficios == null || beneficios.trim().isEmpty()) throw new IllegalArgumentException("Benefícios não podem ser vazios");
        this.beneficios = beneficios;
    }

    @Override
    public String toString() {
        return String.format("Plano[ID=%d, Nome=%s, Valor=R$%.2f/mês, Duração=%d meses, Benefícios=%s]",
                id, nome, valorMensal, duracaoMeses, beneficios);
    }
}
