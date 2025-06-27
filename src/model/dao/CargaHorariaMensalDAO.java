/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.CargaHorariaMensal;
import model.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargaHorariaMensalDAO {
    private Connection connection;

    public CargaHorariaMensalDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(CargaHorariaMensal c) throws SQLException {
        String sql = "INSERT INTO carga_horaria_mensal (professor_id, mes, ano, total_sala, total_complementar, total_geral) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, c.getProfessor().getId());
            stmt.setInt(2, c.getMes());
            stmt.setInt(3, c.getAno());
            stmt.setDouble(4, c.getTotalSala());
            stmt.setDouble(5, c.getTotalComplementar());
            stmt.setDouble(6, c.getTotalGeral());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(CargaHorariaMensal c) throws SQLException {
        String sql = "UPDATE carga_horaria_mensal SET professor_id=?, mes=?, ano=?, total_sala=?, total_complementar=?, total_geral=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, c.getProfessor().getId());
            stmt.setInt(2, c.getMes());
            stmt.setInt(3, c.getAno());
            stmt.setDouble(4, c.getTotalSala());
            stmt.setDouble(5, c.getTotalComplementar());
            stmt.setDouble(6, c.getTotalGeral());
            stmt.setInt(7, c.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM carga_horaria_mensal WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public CargaHorariaMensal buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM carga_horaria_mensal WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearCargaHoraria(rs);
            }
        }
        return null;
    }

    public List<CargaHorariaMensal> listarTodos() throws SQLException {
        String sql = "SELECT * FROM carga_horaria_mensal";
        List<CargaHorariaMensal> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapearCargaHoraria(rs));
            }
        }
        return list;
    }

    private CargaHorariaMensal mapearCargaHoraria(ResultSet rs) throws SQLException {
        CargaHorariaMensal c = new CargaHorariaMensal();
        c.setId(rs.getInt("id"));

        Professor p = new Professor();
        p.setId(rs.getInt("professor_id"));
        c.setProfessor(p);

        c.setMes(rs.getInt("mes"));
        c.setAno(rs.getInt("ano"));
        c.setTotalSala(rs.getDouble("total_sala"));
        c.setTotalComplementar(rs.getDouble("total_complementar"));
        c.setTotalGeral(rs.getDouble("total_geral"));
        return c;
    }
}