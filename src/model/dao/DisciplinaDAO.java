/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;
import model.util.Conexao;


/**
 *
 * @author LASEDi 1781
 */
public class DisciplinaDAO {

    public void inserir(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nome) VALUES (?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.executeUpdate();
        }
    }

    public List<Disciplina> listar() throws SQLException {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                lista.add(d);
            }
        }

        return lista;
    }

    public void atualizar(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}