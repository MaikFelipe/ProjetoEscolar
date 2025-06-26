/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Professor;
import model.util.Conexao;

public class ProfessorDAO {

    public void inserir(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (nome, cpf, email, telefone, observacoes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getEmail());
            stmt.setString(4, professor.getTelefone());
            stmt.setString(5, professor.getObservacoes());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir professor: " + e.getMessage());
        }
    }

    public void atualizar(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET nome = ?, cpf = ?, email = ?, telefone = ?, observacoes = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getEmail());
            stmt.setString(4, professor.getTelefone());
            stmt.setString(5, professor.getObservacoes());
            stmt.setInt(6, professor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM professor WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir professor: " + e.getMessage());
        }
    }

    public Professor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM professor WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setCpf(rs.getString("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setTelefone(rs.getString("telefone"));
                professor.setObservacoes(rs.getString("observacoes"));
                return professor;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor: " + e.getMessage());
        }
        return null;
    }

    public List<Professor> listarTodos() throws SQLException {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professor";

        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setCpf(rs.getString("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setTelefone(rs.getString("telefone"));
                professor.setObservacoes(rs.getString("observacoes"));
                lista.add(professor);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar professores: " + e.getMessage());
        }
        return lista;
    }
}