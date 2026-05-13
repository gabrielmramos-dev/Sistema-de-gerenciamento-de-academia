package dao;

import model.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Plano.
 * Responsável por todas as operações de persistência (CRUD) de planos no banco.
 */
public class PlanoDAO {

    public boolean inserir(Plano plano) {
        String sql = "INSERT INTO plano (nome, descricao, valor_mensal, duracao_meses, beneficios) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setInt(4, plano.getDuracaoMeses());
            stmt.setString(5, plano.getBeneficios());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) plano.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir plano: " + e.getMessage());
        }
        return false;
    }

    public List<Plano> listarTodos() {
        List<Plano> planos = new ArrayList<>();
        String sql = "SELECT * FROM plano ORDER BY id";
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                planos.add(new Plano(
                    rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
                    rs.getDouble("valor_mensal"), rs.getInt("duracao_meses"), rs.getString("beneficios")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar planos: " + e.getMessage());
        }
        return planos;
    }

    public Plano buscarPorId(int id) {
        String sql = "SELECT * FROM plano WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Plano(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"),
                        rs.getDouble("valor_mensal"), rs.getInt("duracao_meses"), rs.getString("beneficios"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar plano: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome = ?, descricao = ?, valor_mensal = ?, duracao_meses = ?, beneficios = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setInt(4, plano.getDuracaoMeses());
            stmt.setString(5, plano.getBeneficios());
            stmt.setInt(6, plano.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar plano: " + e.getMessage());
        }
        return false;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM plano WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir plano: " + e.getMessage());
        }
        return false;
    }
}
