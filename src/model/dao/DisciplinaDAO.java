/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {
    private Connection connection;

    public DisciplinaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Disciplina d) throws SQLException {
        String sql = "INSERT INTO disciplina (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, d.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Disciplina d) throws SQLException {
        String sql = "UPDATE disciplina SET nome=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Disciplina buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearDisciplina(rs);
            }
        }
        return null;
    }

    public List<Disciplina> listarTodos() throws SQLException {
        String sql = "SELECT * FROM disciplina";
        List<Disciplina> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapearDisciplina(rs));
            }
        }
        return list;
    }

    private Disciplina mapearDisciplina(ResultSet rs) throws SQLException {
        Disciplina d = new Disciplina();
        d.setId(rs.getInt("id"));
        d.setNome(rs.getString("nome"));
        return d;
    }
}