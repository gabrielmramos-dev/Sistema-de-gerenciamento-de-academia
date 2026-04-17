import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aluno {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private LocalDate dataMatricula;
    private Plano plano;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento,
                 String telefone, String email, LocalDate dataMatricula, Plano plano) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
        setEmail(email);
        setDataMatricula(dataMatricula);
        setPlano(plano);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public Plano getPlano() { return plano; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo");
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) throw new IllegalArgumentException("CPF não pode ser vazio");
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        if (dataNascimento.isAfter(LocalDate.now())) throw new IllegalArgumentException("Data de nascimento não pode ser futura");
        this.dataNascimento = dataNascimento;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) throw new IllegalArgumentException("Telefone não pode ser vazio");
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Email inválido");
        this.email = email;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        if (dataMatricula == null) throw new IllegalArgumentException("Data de matrícula não pode ser nula");
        this.dataMatricula = dataMatricula;
    }

    public void setPlano(Plano plano) {
        if (plano == null) throw new IllegalArgumentException("Plano não pode ser nulo");
        this.plano = plano;
    }

    public LocalDate getDataVencimentoPlano() {
        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    public boolean isPlanoAtivo() {
        return LocalDate.now().isBefore(getDataVencimentoPlano()) ||
               LocalDate.now().isEqual(getDataVencimentoPlano());
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
