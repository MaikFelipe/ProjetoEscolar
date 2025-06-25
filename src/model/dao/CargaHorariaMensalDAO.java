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
import model.CargaHorariaMensal;
import model.util.Conexao;
import java.util.ArrayList;
import java.util.List;
import model.Professor;

public class CargaHorariaMensalDAO {
    
    public void inserir(CargaHorariaMensal chm) throws SQLException {
        String sql = "INSERT INTO carga_horaria_mensal (professor_id, mes, ano, total_sala, total_complementar, total_geral) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chm.getProfessor().getId());
            stmt.setInt(2, chm.getMes());
            stmt.setInt(3, chm.getAno());
            stmt.setDouble(4, chm.getTotalSala());
            stmt.setDouble(5, chm.getTotalComplementar());
            stmt.setDouble(6, chm.getTotalGeral());
            stmt.executeUpdate();
        }
    }

    public List<CargaHorariaMensal> listar() throws SQLException {
        List<CargaHorariaMensal> lista = new ArrayList<>();
        String sql = "SELECT * FROM carga_horaria_mensal";
        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CargaHorariaMensal chm = new CargaHorariaMensal();
                chm.setId(rs.getInt("id"));

                Professor professor = new Professor();
                professor.setId(rs.getInt("professor_id"));
                chm.setProfessor(professor);

                chm.setMes(rs.getInt("mes"));
                chm.setAno(rs.getInt("ano"));
                chm.setTotalSala(rs.getDouble("total_sala"));
                chm.setTotalComplementar(rs.getDouble("total_complementar"));
                chm.setTotalGeral(rs.getDouble("total_geral"));

                lista.add(chm);
            }
        }
        return lista;
    }

    public void atualizar(CargaHorariaMensal chm) throws SQLException {
        String sql = "UPDATE carga_horaria_mensal SET professor_id=?, mes=?, ano=?, total_sala=?, total_complementar=?, total_geral=? WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chm.getProfessor().getId());
            stmt.setInt(2, chm.getMes());
            stmt.setInt(3, chm.getAno());
            stmt.setDouble(4, chm.getTotalSala());
            stmt.setDouble(5, chm.getTotalComplementar());
            stmt.setDouble(6, chm.getTotalGeral());
            stmt.setInt(7, chm.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM carga_horaria_mensal WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}