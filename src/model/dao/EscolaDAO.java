/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Escola;
import model.Usuario;
import model.dao.UsuarioDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscolaDAO {
    private Connection connection;

    public EscolaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Escola e) throws SQLException {
        String sql = "INSERT INTO escola (nome, endereco, telefone, email, diretor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getEnderecoCompleto());
            stmt.setString(3, e.getTelefone());
            stmt.setString(4, e.getEmail());
            stmt.setString(5, e.getUsuarioDiretor() != null ? e.getUsuarioDiretor().getLogin() : null);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Escola e) throws SQLException {
        String sql = "UPDATE escola SET nome=?, endereco=?, telefone=?, email=?, diretor=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getEnderecoCompleto());
            stmt.setString(3, e.getTelefone());
            stmt.setString(4, e.getEmail());
            stmt.setString(5, e.getUsuarioDiretor() != null ? e.getUsuarioDiretor().getLogin() : null);
            stmt.setInt(6, e.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM escola WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Escola buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM escola WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapearEscola(rs);
            }
        }
        return null;
    }

    public List<Escola> listarTodos() throws SQLException {
        String sql = "SELECT * FROM escola";
        List<Escola> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapearEscola(rs));
            }
        }
        return list;
    }

    private Escola mapearEscola(ResultSet rs) throws SQLException {
        Escola e = new Escola();
        e.setId(rs.getInt("id"));
        e.setNome(rs.getString("nome"));
        e.setEnderecoCompleto(rs.getString("endereco"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));

        String loginDiretor = rs.getString("diretor");
        Usuario diretor = null;
        if (loginDiretor != null) {
            try {
                UsuarioDao usuarioDao = new UsuarioDao();
                diretor = usuarioDao.buscarPorLogin(loginDiretor);
            } catch (SQLException ex) {
                diretor = null;
            }
        }

        e.setUsuarioDiretor(diretor);
        return e;
    }
}
