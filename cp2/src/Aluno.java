import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclasse de Pessoa que representa um aluno da academia.
 * Mantém todos os atributos e comportamentos do CP1,
 * agora com herança de Pessoa e sobrecarga de calcularMensalidade.
 */
public class Aluno extends Pessoa {
    private String email;
    private LocalDate dataNascimento;
    private LocalDate dataMatricula;
    private Plano plano;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento,
                 String telefone, String email, LocalDate dataMatricula, Plano plano) {
        super(id, nome, cpf, telefone); // chama construtor da superclasse
        setDataNascimento(dataNascimento);
        setEmail(email);
        setDataMatricula(dataMatricula);
        setPlano(plano);
    }

    public String getEmail() { return email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public Plano getPlano() { return plano; }

    public void setEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");
        this.email = email;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null)
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        if (dataNascimento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento não pode ser futura");
        this.dataNascimento = dataNascimento;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        if (dataMatricula == null)
            throw new IllegalArgumentException("Data de matrícula não pode ser nula");
        this.dataMatricula = dataMatricula;
    }

    public void setPlano(Plano plano) {
        if (plano == null)
            throw new IllegalArgumentException("Plano não pode ser nulo");
        this.plano = plano;
    }

    // =========================================================
    // MÉTODOS DO CP1 MANTIDOS
    // =========================================================

    public LocalDate getDataVencimentoPlano() {
        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    public boolean isPlanoAtivo() {
        return !LocalDate.now().isAfter(getDataVencimentoPlano());
    }

    // =========================================================
    // SOBRECARGA DE MÉTODOS (Overloading) - Requisito CP2
    // Mesmo nome "calcularMensalidade", parâmetros diferentes
    // =========================================================

    /**
     * Calcula a mensalidade com base no plano atual do aluno.
     */
    public double calcularMensalidade() {
        return plano.getValorMensal();
    }

    /**
     * Calcula a mensalidade aplicando um percentual de desconto.
     *
     * @param percentualDesconto valor entre 0.0 e 1.0 (ex: 0.10 = 10%)
     */
    public double calcularMensalidade(double percentualDesconto) {
        if (percentualDesconto < 0 || percentualDesconto > 1)
            throw new IllegalArgumentException("Desconto deve estar entre 0.0 e 1.0");
        return plano.getValorMensal() * (1 - percentualDesconto);
    }

    /**
     * Calcula a mensalidade com desconto em reais e acréscimo de taxa.
     *
     * @param descontoReais  valor fixo de desconto em R$
     * @param taxaAdicional  valor fixo de taxa extra em R$
     */
    public double calcularMensalidade(double descontoReais, double taxaAdicional) {
        double resultado = plano.getValorMensal() - descontoReais + taxaAdicional;
        return Math.max(resultado, 0);
    }

    /**
     * Calcula a mensalidade de acordo com o nome de um plano promocional.
     *
     * @param nomePlanoPromocional "ESTUDANTE", "SENIOR" ou "CONVÊNIO"
     */
    public double calcularMensalidade(String nomePlanoPromocional) {
        double base = plano.getValorMensal();
        return switch (nomePlanoPromocional.toUpperCase()) {
            case "ESTUDANTE" -> base * 0.80; // 20% de desconto
            case "SENIOR"    -> base * 0.75; // 25% de desconto
            case "CONVENIO"  -> base * 0.70; // 30% de desconto
            default          -> base;
        };
    }

    // =========================================================
    // SOBRESCRITA (Override) - Requisito CP2
    // =========================================================

    /**
     * Custo mensal do aluno é o valor do plano.
     */
    @Override
    public double calcularCustoMensal() {
        return calcularMensalidade();
    }

    /**
     * Exibe informações completas do aluno,
     * aproveitando o método da superclasse com super.exibirInfo().
     */
    @Override
    public void exibirInfo() {
        super.exibirInfo(); // chama exibição base de Pessoa
        System.out.println("Email     : " + email);
        System.out.println("Nascimento: " + dataNascimento.format(FMT));
        System.out.println("Matrícula : " + dataMatricula.format(FMT));
        System.out.println("Plano     : " + plano.getNome() +
                " (R$" + String.format("%.2f", plano.getValorMensal()) + "/mês)");
        System.out.println("Vencimento: " + getDataVencimentoPlano().format(FMT));
        System.out.println("Status    : " + (isPlanoAtivo() ? "ATIVO" : "VENCIDO"));
    }

    @Override
    public String toString() {
        return String.format(
            "Aluno[ID=%d, Nome=%s, CPF=%s, Tel=%s, Email=%s, Matrícula=%s, Plano=%s, Vencimento=%s, Status=%s]",
            id, nome, cpf, telefone, email,
            dataMatricula.format(FMT),
            plano.getNome(),
            getDataVencimentoPlano().format(FMT),
            isPlanoAtivo() ? "ATIVO" : "VENCIDO"
        );
    }
}
