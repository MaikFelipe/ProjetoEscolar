/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model.dao;

import model.Turma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.util.Conexao;

/**
 *
 * @author LASEDi 1781
 */

public class TurmaDAO {

    public void inserir(Turma turma) throws SQLException {
        String sql = "INSERT INTO turma (nome, serie, nivel_ensino, ano_letivo, turno, numero_min_alunos, numero_max_alunos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSerie());
            stmt.setString(3, turma.getNivelEnsino());
            stmt.setInt(4, turma.getAnoLetivo());
            stmt.setString(5, turma.getTurno());
            stmt.setInt(6, turma.getNumeroMinimoAlunos());
            stmt.setInt(7, turma.getNumeroMaximoAlunos());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir turma: " + e.getMessage());
        }
    }

    public void atualizar(Turma turma) throws SQLException {
        String sql = "UPDATE turma SET nome=?, serie=?, nivel_ensino=?, ano_letivo=?, turno=?, numero_min_alunos=?, numero_max_alunos=? WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSerie());
            stmt.setString(3, turma.getNivelEnsino());
            stmt.setInt(4, turma.getAnoLetivo());
            stmt.setString(5, turma.getTurno());
            stmt.setInt(6, turma.getNumeroMinimoAlunos());
            stmt.setInt(7, turma.getNumeroMaximoAlunos());
            stmt.setInt(8, turma.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM turma WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
        }
    }

    public Turma buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM turma WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setSerie(rs.getString("serie"));
                turma.setNivelEnsino(rs.getString("nivel_ensino"));
                turma.setAnoLetivo(rs.getInt("ano_letivo"));
                turma.setTurno(rs.getString("turno"));
                turma.setNumeroMinimoAlunos(rs.getInt("numero_min_alunos"));
                turma.setNumeroMaximoAlunos(rs.getInt("numero_max_alunos"));
                return turma;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma: " + e.getMessage());
        }
        return null;
    }

    public List<Turma> listarTodas() throws SQLException {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM turma";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setSerie(rs.getString("serie"));
                turma.setNivelEnsino(rs.getString("nivel_ensino"));
                turma.setAnoLetivo(rs.getInt("ano_letivo"));
                turma.setTurno(rs.getString("turno"));
                turma.setNumeroMinimoAlunos(rs.getInt("numero_min_alunos"));
                turma.setNumeroMaximoAlunos(rs.getInt("numero_max_alunos"));
                lista.add(turma);
            }

        } catch (SQLException e)  {
            System.err.println("Erro ao listar turmas: " + e.getMessage());
        }
        return lista;
    }
}