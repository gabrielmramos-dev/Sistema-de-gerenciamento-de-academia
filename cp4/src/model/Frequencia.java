package model;

import java.time.LocalDateTime;

/**
 * Representa um registro de entrada (frequência) de um Aluno na academia.
 */
public class Frequencia {
    private int id;
    private Aluno aluno;
    private LocalDateTime dataHoraEntrada;

    public Frequencia(int id, Aluno aluno, LocalDateTime dataHoraEntrada) {
        this.id = id;
        this.aluno = aluno;
        this.dataHoraEntrada = dataHoraEntrada;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }
}
