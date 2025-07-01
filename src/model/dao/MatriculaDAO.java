/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Matricula;
import model.Nota;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {
    private Connection connection;

    public MatriculaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Matricula m) throws SQLException {
        String sql = "INSERT INTO matricula (aluno_id, turma_id, data_matricula, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, m.getAluno().getId());
            stmt.setInt(2, m.getTurma().getId());
            stmt.setDate(3, Date.valueOf(m.getDataMatricula()));
            stmt.setString(4, m.getStatus());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Matricula m) throws SQLException {
        String sql = "UPDATE matricula SET aluno_id=?, turma_id=?, data_matricula=?, status=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, m.getAluno().getId());
            stmt.setInt(2, m.getTurma().getId());
            stmt.setDate(3, Date.valueOf(m.getDataMatricula()));
            stmt.setString(4, m.getStatus());
            stmt.setInt(5, m.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Matricula buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Matricula> listarTodos() throws SQLException {
        String sql = "SELECT * FROM matricula";
        List<Matricula> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapear(rs));
        }
        return list;
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        Matricula m = new Matricula();
        m.setId(rs.getInt("id"));
        model.Aluno a = new model.Aluno();
        a.setId(rs.getInt("aluno_id"));
        m.setAluno(a);
        model.Turma t = new model.Turma();
        t.setId(rs.getInt("turma_id"));
        m.setTurma(t);
        m.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
        m.setStatus(rs.getString("status"));
        m.setHistoricoNotas(new ArrayList<>());
        return m;
    }
}