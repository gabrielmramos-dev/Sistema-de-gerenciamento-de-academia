package dao;

import model.Aula;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade InscricaoAula.
 * Expõe operações de leitura e cancelamento de inscrições.
 * A lógica de validação de negócio para inscrição está em {@link service.InscricaoService}.
 */
public class InscricaoDAO {

    /**
     * Persiste a inscrição de um aluno em uma aula no banco de dados.
     * Deve ser chamado apenas pelo InscricaoService após todas as validações.
     */
    public boolean salvarInscricao(int idAluno, int idAula) {
        String sql = "INSERT INTO inscricao_aula (id_aluno, id_aula) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idAula);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar inscrição: " + e.getMessage());
        }
        return false;
    }

    /** Cancela a inscrição de um aluno em uma aula específica. */
    public boolean cancelarInscricao(int idAluno, int idAula) {
        String sql = "DELETE FROM inscricao_aula WHERE id_aluno = ? AND id_aula = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idAula);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar inscrição: " + e.getMessage());
        }
        return false;
    }

    /** Retorna todas as aulas em que um aluno está inscrito. */
    public List<Aula> listarAulasPorAluno(int idAluno) {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT a.* FROM aula a JOIN inscricao_aula i ON a.id = i.id_aula WHERE i.id_aluno = ? ORDER BY a.horario";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    aulas.add(new Aula(
                        rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
                        rs.getInt("capacidade_maxima"), rs.getTimestamp("horario").toLocalDateTime(),
                        rs.getInt("duracao_minutos"), null
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aulas do aluno: " + e.getMessage());
        }
        return aulas;
    }

    /** Conta quantos alunos estão inscritos em uma aula. */
    public int contarInscritosNaAula(int idAula) {
        String sql = "SELECT COUNT(*) FROM inscricao_aula WHERE id_aula = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar inscritos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Busca uma aula que conflite de horário com as aulas já inscritas de um aluno.
     * Usada pelo InscricaoService para detectar conflitos de agenda.
     */
    public Aula buscarConflitoHorario(int idAluno, LocalDateTime horario) {
        String sql = "SELECT a.* FROM aula a JOIN inscricao_aula i ON a.id = i.id_aula " +
                     "WHERE i.id_aluno = ? AND a.horario = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setTimestamp(2, Timestamp.valueOf(horario));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aula(rs.getInt("id"), rs.getString("nome"), "", 0,
                        rs.getTimestamp("horario").toLocalDateTime(), 0, null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar conflito de horário: " + e.getMessage());
        }
        return null;
    }
}
