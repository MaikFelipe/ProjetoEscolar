/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Municipio;
import model.Usuario;
import java.sql.*;
import java.util.*;

public class MunicipioDAO {
    private Connection connection;

    public MunicipioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Municipio m) throws SQLException {
        String sql = "INSERT INTO municipio (nome, estado, secretario_educacao_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getEstado());
            stmt.setInt(3, m.getSecretarioEducacao().getId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Municipio m) throws SQLException {
        String sql = "UPDATE municipio SET nome=?, estado=?, secretario_educacao_id=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getEstado());
            stmt.setInt(3, m.getSecretarioEducacao().getId());
            stmt.setInt(4, m.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM municipio WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Municipio buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM municipio WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Municipio> listarTodos() throws SQLException {
        String sql = "SELECT * FROM municipio";
        List<Municipio> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapear(rs));
        }
        return list;
    }

    private Municipio mapear(ResultSet rs) throws SQLException {
        Municipio m = new Municipio();
        m.setId(rs.getInt("id"));
        m.setNome(rs.getString("nome"));
        m.setEstado(rs.getString("estado"));

        Usuario secretario = new Usuario();
        secretario.setId(rs.getInt("secretario_educacao_id"));
        m.setSecretarioEducacao(secretario);

        return m;
    }
}