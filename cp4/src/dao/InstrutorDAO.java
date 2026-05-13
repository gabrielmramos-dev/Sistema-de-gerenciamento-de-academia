package dao;

import model.Instrutor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Instrutor.
 * Responsável por todas as operações de persistência (CRUD) de instrutores no banco.
 */
public class InstrutorDAO {

    public boolean inserir(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, cpf, telefone, especialidade, horarios_trabalho) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setString(3, instrutor.getTelefone());
            stmt.setString(4, instrutor.getEspecialidade());
            stmt.setString(5, instrutor.getHorariosTrabalho());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) instrutor.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir instrutor: " + e.getMessage());
        }
        return false;
    }

    public List<Instrutor> listarTodos() {
        List<Instrutor> instrutores = new ArrayList<>();
        String sql = "SELECT * FROM instrutor ORDER BY nome";
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                instrutores.add(new Instrutor(
                    rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"),
                    rs.getString("telefone"), rs.getString("especialidade"), rs.getString("horarios_trabalho")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar instrutores: " + e.getMessage());
        }
        return instrutores;
    }

    public Instrutor buscarPorId(int id) {
        String sql = "SELECT * FROM instrutor WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Instrutor(
                        rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("telefone"), rs.getString("especialidade"), rs.getString("horarios_trabalho")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar instrutor: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome = ?, cpf = ?, telefone = ?, especialidade = ?, horarios_trabalho = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setString(3, instrutor.getTelefone());
            stmt.setString(4, instrutor.getEspecialidade());
            stmt.setString(5, instrutor.getHorariosTrabalho());
            stmt.setInt(6, instrutor.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar instrutor: " + e.getMessage());
        }
        return false;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM instrutor WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir instrutor: " + e.getMessage());
        }
        return false;
    }
}
