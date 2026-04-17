import java.util.ArrayList;

public class Aula {
    private int id;
    private String nome;
    private String descricao;
    private int capacidadeMaxima;
    private String horario;
    private int duracaoMinutos;
    private Instrutor instrutor;
    private ArrayList<Aluno> alunosInscritos;

    public Aula(int id, String nome, String descricao, int capacidadeMaxima,
                String horario, int duracaoMinutos, Instrutor instrutor) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
        setCapacidadeMaxima(capacidadeMaxima);
        setHorario(horario);
        setDuracaoMinutos(duracaoMinutos);
        setInstrutor(instrutor);
        this.alunosInscritos = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public String getHorario() { return horario; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public Instrutor getInstrutor() { return instrutor; }
    public ArrayList<Aluno> getAlunosInscritos() { return alunosInscritos; }

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

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) throw new IllegalArgumentException("Capacidade máxima deve ser maior que zero");
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public void setHorario(String horario) {
        if (horario == null || horario.trim().isEmpty()) throw new IllegalArgumentException("Horário não pode ser vazio");
        this.horario = horario;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        if (duracaoMinutos <= 0) throw new IllegalArgumentException("Duração deve ser maior que zero");
        this.duracaoMinutos = duracaoMinutos;
    }

    public void setInstrutor(Instrutor instrutor) {
        if (instrutor == null) throw new IllegalArgumentException("Instrutor não pode ser nulo");
        this.instrutor = instrutor;
    }

    public int getQuantidadeInscritos() {
        return alunosInscritos.size();
    }

    public boolean isLotada() {
        return alunosInscritos.size() >= capacidadeMaxima;
    }

    public boolean alunoJaInscrito(Aluno aluno) {
        for (Aluno a : alunosInscritos) {
            if (a.getId() == aluno.getId()) return true;
        }
        return false;
    }

    public void inscreverAluno(Aluno aluno) {
        alunosInscritos.add(aluno);
    }

    public boolean cancelarInscricao(Aluno aluno) {
        return alunosInscritos.removeIf(a -> a.getId() == aluno.getId());
    }

    @Override
    public String toString() {
        return String.format("Aula[ID=%d, Nome=%s, Horário=%s, Duração=%dmin, Inscritos=%d/%d, Instrutor=%s]",
                id, nome, horario, duracaoMinutos, getQuantidadeInscritos(), capacidadeMaxima, instrutor.getNome());
    }
}
