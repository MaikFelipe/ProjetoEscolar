/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */

import model.Frequencia;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FrequenciaDAO {
    private Connection connection;

    public FrequenciaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Frequencia f) throws SQLException {
        String sql = "INSERT INTO frequencia (aluno_id, turma_id, data, presente, total_faltas_acumuladas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, f.getAluno().getId());
            stmt.setInt(2, f.getTurma().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setBoolean(4, f.isPresente());
            stmt.setInt(5, f.getTotalFaltasAcumuladas());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Frequencia f) throws SQLException {
        String sql = "UPDATE frequencia SET aluno_id=?, turma_id=?, data=?, presente=?, total_faltas_acumuladas=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, f.getAluno().getId());
            stmt.setInt(2, f.getTurma().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setBoolean(4, f.isPresente());
            stmt.setInt(5, f.getTotalFaltasAcumuladas());
            stmt.setInt(6, f.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM frequencia WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Frequencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM frequencia WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Frequencia> listarTodos() throws SQLException {
        String sql = "SELECT * FROM frequencia";
        List<Frequencia> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapear(rs));
        }
        return list;
    }

    private Frequencia mapear(ResultSet rs) throws SQLException {
        Frequencia f = new Frequencia();
        f.setId(rs.getInt("id"));
        model.Aluno a = new model.Aluno();
        a.setId(rs.getInt("aluno_id"));
        f.setAluno(a);
        model.Turma t = new model.Turma();
        t.setId(rs.getInt("turma_id"));
        f.setTurma(t);
        f.setData(rs.getDate("data").toLocalDate());
        f.setPresente(rs.getBoolean("presente"));
        f.setTotalFaltasAcumuladas(rs.getInt("total_faltas_acumuladas"));
        return f;
    }
}