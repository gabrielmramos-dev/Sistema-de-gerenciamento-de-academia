package dao;

import model.Aluno;
import model.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Aluno.
 * Responsável por todas as operações de persistência (CRUD) de alunos no banco.
 */
public class AlunoDAO {

    public boolean inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, telefone, email, data_nascimento, data_matricula, id_plano) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));
            if (aluno.getPlano() != null) stmt.setInt(7, aluno.getPlano().getId());
            else stmt.setNull(7, Types.INTEGER);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) aluno.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
        }
        return false;
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT a.*, p.nome as plano_nome, p.duracao_meses FROM aluno a LEFT JOIN plano p ON a.id_plano = p.id ORDER BY a.nome";
        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plano p = null;
                if (rs.getInt("id_plano") != 0) {
                    p = new Plano(rs.getInt("id_plano"), rs.getString("plano_nome"), "", 0, rs.getInt("duracao_meses"), "");
                }
                alunos.add(new Aluno(
                    rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone"),
                    rs.getString("email"), rs.getDate("data_nascimento").toLocalDate(),
                    rs.getDate("data_matricula").toLocalDate(), p
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public Aluno buscarPorId(int id) {
        String sql = "SELECT a.*, p.nome as plano_nome, p.duracao_meses FROM aluno a LEFT JOIN plano p ON a.id_plano = p.id WHERE a.id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Plano p = null;
                    if (rs.getInt("id_plano") != 0) {
                        p = new Plano(rs.getInt("id_plano"), rs.getString("plano_nome"), "", 0, rs.getInt("duracao_meses"), "");
                    }
                    return new Aluno(
                        rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone"),
                        rs.getString("email"), rs.getDate("data_nascimento").toLocalDate(),
                        rs.getDate("data_matricula").toLocalDate(), p
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, cpf = ?, telefone = ?, email = ?, data_nascimento = ?, data_matricula = ?, id_plano = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));
            if (aluno.getPlano() != null) stmt.setInt(7, aluno.getPlano().getId());
            else stmt.setNull(7, Types.INTEGER);
            stmt.setInt(8, aluno.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
        return false;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        }
        return false;
    }
}
