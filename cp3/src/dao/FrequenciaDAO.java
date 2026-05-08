package dao;

import model.Frequencia;
import model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class FrequenciaDAO {

    public boolean registrarEntrada(int idAluno) {
        String sql = "INSERT INTO frequencia (id_aluno) VALUES (?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar frequência: " + e.getMessage());
        }
        return false;
    }

    public List<Frequencia> listarPorAluno(int idAluno) {
        List<Frequencia> frequencias = new ArrayList<>();
        String sql = "SELECT * FROM frequencia WHERE id_aluno = ? ORDER BY data_hora_entrada DESC";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    frequencias.add(new Frequencia(
                        rs.getInt("id"),
                        null, // Aluno será preenchido se necessário fora daqui
                        rs.getTimestamp("data_hora_entrada").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar frequências: " + e.getMessage());
        }
        return frequencias;
    }

    public int contarVisitas(int idAluno) {
        String sql = "SELECT COUNT(*) FROM frequencia WHERE id_aluno = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar visitas: " + e.getMessage());
        }
        return 0;
    }

    public LocalDateTime buscarUltimaVisita(int idAluno) {
        String sql = "SELECT MAX(data_hora_entrada) FROM frequencia WHERE id_aluno = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getTimestamp(1) != null) {
                    return rs.getTimestamp(1).toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar última visita: " + e.getMessage());
        }
        return null;
    }
}
