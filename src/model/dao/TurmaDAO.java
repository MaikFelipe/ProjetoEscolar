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

public class TurmaDAO {

    private Connection connection;

    public TurmaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Turma turma) throws SQLException {
        String sql = "INSERT INTO turma (nome, serie, nivel_ensino, ano_letivo, turno, numero_min_alunos, numero_max_alunos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSerie());
            stmt.setString(3, turma.getNivelEnsino());
            stmt.setInt(4, turma.getAnoLetivo());
            stmt.setString(5, turma.getTurno());
            stmt.setInt(6, turma.getNumeroMinimoAlunos());
            stmt.setInt(7, turma.getNumeroMaximoAlunos());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Turma turma) throws SQLException {
        String sql = "UPDATE turma SET nome=?, serie=?, nivel_ensino=?, ano_letivo=?, turno=?, numero_min_alunos=?, numero_max_alunos=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getSerie());
            stmt.setString(3, turma.getNivelEnsino());
            stmt.setInt(4, turma.getAnoLetivo());
            stmt.setString(5, turma.getTurno());
            stmt.setInt(6, turma.getNumeroMinimoAlunos());
            stmt.setInt(7, turma.getNumeroMaximoAlunos());
            stmt.setInt(8, turma.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM turma WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Turma buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM turma WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        }
        return null;
    }

    public List<Turma> listarTodas() throws SQLException {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM turma";
        try (Statement stmt = connection.createStatement();
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
        }
        return lista;
    }
}
