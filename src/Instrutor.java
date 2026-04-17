public class Instrutor {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String especialidade;
    private String horariosTrabalho;

    public Instrutor(int id, String nome, String cpf, String telefone,
                     String especialidade, String horariosTrabalho) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEspecialidade(especialidade);
        setHorariosTrabalho(horariosTrabalho);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getEspecialidade() { return especialidade; }
    public String getHorariosTrabalho() { return horariosTrabalho; }

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

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) throw new IllegalArgumentException("Telefone não pode ser vazio");
        this.telefone = telefone;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) throw new IllegalArgumentException("Especialidade não pode ser vazia");
        this.especialidade = especialidade;
    }

    public void setHorariosTrabalho(String horariosTrabalho) {
        if (horariosTrabalho == null || horariosTrabalho.trim().isEmpty()) throw new IllegalArgumentException("Horários de trabalho não podem ser vazios");
        this.horariosTrabalho = horariosTrabalho;
    }

    @Override
    public String toString() {
        return String.format("Instrutor[ID=%d, Nome=%s, CPF=%s, Tel=%s, Especialidade=%s, Horários=%s]",
                id, nome, cpf, telefone, especialidade, horariosTrabalho);
    }
}
