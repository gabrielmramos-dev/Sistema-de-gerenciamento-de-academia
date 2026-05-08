package dao;

import model.Aula;
import model.Instrutor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class AulaDAO {

    public boolean inserir(Aula aula) {
        String sql = "INSERT INTO aula (nome, descricao, capacidade_maxima, horario, duracao_minutos, id_instrutor) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aula.getNome());
            stmt.setString(2, aula.getDescricao());
            stmt.setInt(3, aula.getCapacidadeMaxima());
            stmt.setTimestamp(4, Timestamp.valueOf(aula.getHorario()));
            stmt.setInt(5, aula.getDuracaoMinutos());
            if (aula.getInstrutor() != null) {
                stmt.setInt(6, aula.getInstrutor().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        aula.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aula: " + e.getMessage());
        }
        return false;
    }

    public List<Aula> listarTodas() {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT a.*, i.nome as instrutor_nome FROM aula a LEFT JOIN instrutor i ON a.id_instrutor = i.id ORDER BY a.horario";
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Instrutor i = null;
                if (rs.getInt("id_instrutor") != 0) {
                    i = new Instrutor(rs.getInt("id_instrutor"), rs.getString("instrutor_nome"), "", "", "", "");
                }
                aulas.add(new Aula(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getInt("capacidade_maxima"),
                    rs.getTimestamp("horario").toLocalDateTime(),
                    rs.getInt("duracao_minutos"),
                    i
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aulas: " + e.getMessage());
        }
        return aulas;
    }

    public Aula buscarPorId(int id) {
        String sql = "SELECT a.*, i.nome as instrutor_nome FROM aula a LEFT JOIN instrutor i ON a.id_instrutor = i.id WHERE a.id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Instrutor i = null;
                    if (rs.getInt("id_instrutor") != 0) {
                        i = new Instrutor(rs.getInt("id_instrutor"), rs.getString("instrutor_nome"), "", "", "", "");
                    }
                    return new Aula(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("capacidade_maxima"),
                        rs.getTimestamp("horario").toLocalDateTime(),
                        rs.getInt("duracao_minutos"),
                        i
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aula: " + e.getMessage());
        }
        return null;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM aula WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aula: " + e.getMessage());
        }
        return false;
    }
}
