package dao;

import model.Aluno;
import model.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class InscricaoDAO {

    /**
     * Realiza a inscrição de um aluno em uma aula, validando plano, capacidade e horários.
     * Requisito: Regra de Negócio Complexa do CP3.
     */
    public String inscreverAluno(int idAluno, int idAula) {
        // 1. Verificar se o plano do aluno está ativo
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        
        if (aluno == null) return "❌ Erro: Aluno não encontrado.";
        
        if (!aluno.isPlanoAtivo()) {
            return "❌ Erro: O plano do aluno está vencido (Vencimento: " + aluno.getDataVencimentoPlano() + "). Inscrição não permitida.";
        }

        // 2. Verificar se a aula existe e se não atingiu a capacidade máxima
        AulaDAO aulaDAO = new AulaDAO();
        Aula aula = aulaDAO.buscarPorId(idAula);
        
        if (aula == null) return "❌ Erro: Aula não encontrada.";
        
        int totalInscritos = contarInscritosNaAula(idAula);
        if (totalInscritos >= aula.getCapacidadeMaxima()) {
            return "❌ Erro: A aula atingiu a capacidade máxima (" + totalInscritos + "/" + aula.getCapacidadeMaxima() + ").";
        }

        // 3. Verificar se o aluno já não tem outra aula no mesmo horário
        Aula aulaConflito = buscarConflitoHorario(idAluno, aula.getHorario());
        if (aulaConflito != null) {
            return "❌ Erro: Conflito de horário! O aluno já está inscrito na aula '" + aulaConflito.getNome() + "' às " + aulaConflito.getHorario() + ".";
        }

        // 4. Se passou em todas as validações, confirmar a inscrição
        String sql = "INSERT INTO inscricao_aula (id_aluno, id_aula) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idAula);
            stmt.executeUpdate();
            return "✅ Sucesso: Aluno inscrito com sucesso na aula " + aula.getNome() + "!";
        } catch (SQLException e) {
            return "❌ Erro ao processar inscrição no banco: " + e.getMessage();
        }
    }

    private int contarInscritosNaAula(int idAula) {
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

    private Aula buscarConflitoHorario(int idAluno, LocalDateTime horario) {
        String sql = "SELECT a.* FROM aula a JOIN inscricao_aula i ON a.id = i.id_aula " +
                     "WHERE i.id_aluno = ? AND a.horario = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setTimestamp(2, Timestamp.valueOf(horario));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aula(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        "", 0,
                        rs.getTimestamp("horario").toLocalDateTime(),
                        0, null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar conflito de horário: " + e.getMessage());
        }
        return null;
    }

    public List<Aula> listarAulasPorAluno(int idAluno) {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT a.* FROM aula a JOIN inscricao_aula i ON a.id = i.id_aula WHERE i.id_aluno = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    aulas.add(new Aula(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("capacidade_maxima"),
                        rs.getTimestamp("horario").toLocalDateTime(),
                        rs.getInt("duracao_minutos"),
                        null
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aulas do aluno: " + e.getMessage());
        }
        return aulas;
    }
}
