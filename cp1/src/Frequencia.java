import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Frequencia {
    private int id;
    private Aluno aluno;
    private LocalDateTime dataHoraEntrada;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Frequencia(int id, Aluno aluno, LocalDateTime dataHoraEntrada) {
        setId(id);
        setAluno(aluno);
        setDataHoraEntrada(dataHoraEntrada);
    }

    public int getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo");
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno não pode ser nulo");
        this.aluno = aluno;
    }

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        if (dataHoraEntrada == null) throw new IllegalArgumentException("Data/hora de entrada não pode ser nula");
        if (dataHoraEntrada.isAfter(LocalDateTime.now())) throw new IllegalArgumentException("Data/hora de entrada não pode ser futura");
        this.dataHoraEntrada = dataHoraEntrada;
    }

    @Override
    public String toString() {
        return String.format("Frequencia[ID=%d, Aluno=%s, Entrada=%s]",
                id, aluno.getNome(), dataHoraEntrada.format(FMT));
    }
}
