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
import model.Municipio;
import model.util.Conexao;

public class MunicipioDAO {

    public void inserir(Municipio m) throws SQLException {
        String sql = "INSERT INTO municipio (nome, estado, secretario_educacao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getEstado());
            stmt.setString(3, m.getSecretarioEducacao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Municipio m) throws SQLException {
        String sql = "UPDATE municipio SET nome = ?, estado = ?, secretario_educacao = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getEstado());
            stmt.setString(3, m.getSecretarioEducacao());
            stmt.setInt(4, m.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM municipio WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Municipio buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM municipio WHERE id = ?";
        Municipio m = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                m = new Municipio();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setEstado(rs.getString("estado"));
                m.setSecretarioEducacao(rs.getString("secretario_educacao"));
            }
        }

        return m;
    }

    public List<Municipio> listarTodos() throws SQLException {
        String sql = "SELECT * FROM municipio";
        List<Municipio> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Municipio m = new Municipio();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setEstado(rs.getString("estado"));
                m.setSecretarioEducacao(rs.getString("secretario_educacao"));
                lista.add(m);
            }
        }

        return lista;
    }
}
