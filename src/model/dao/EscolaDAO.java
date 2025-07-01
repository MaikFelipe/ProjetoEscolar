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
import model.Municipio;
import model.Usuario;
import java.sql.*;
import java.util.*;

public class EscolaDAO {
    private Connection connection;

    public EscolaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Escola e) throws SQLException {
        String sql = "INSERT INTO escola (nome, endereco_completo, bairro, municipio_id, telefone, email, usuario_diretor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getEnderecoCompleto());
            stmt.setString(3, e.getBairro());
            stmt.setInt(4, e.getMunicipio().getId());
            stmt.setString(5, e.getTelefone());
            stmt.setString(6, e.getEmail());
            stmt.setInt(7, e.getUsuarioDiretor().getId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Escola e) throws SQLException {
        String sql = "UPDATE escola SET nome=?, endereco_completo=?, bairro=?, municipio_id=?, telefone=?, email=?, usuario_diretor_id=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getEnderecoCompleto());
            stmt.setString(3, e.getBairro());
            stmt.setInt(4, e.getMunicipio().getId());
            stmt.setString(5, e.getTelefone());
            stmt.setString(6, e.getEmail());
            stmt.setInt(7, e.getUsuarioDiretor().getId());
            stmt.setInt(8, e.getId());
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
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Escola> listarTodos() throws SQLException {
        String sql = "SELECT * FROM escola";
        List<Escola> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapear(rs));
        }
        return list;
    }

    private Escola mapear(ResultSet rs) throws SQLException {
        Escola e = new Escola();
        Municipio municipio = new Municipio();
        municipio.setId(rs.getInt("municipio_id"));
        Usuario diretor = new Usuario();
        diretor.setId(rs.getInt("usuario_diretor_id"));

        e.setId(rs.getInt("id"));
        e.setNome(rs.getString("nome"));
        e.setEnderecoCompleto(rs.getString("endereco_completo"));
        e.setBairro(rs.getString("bairro"));
        e.setMunicipio(municipio);
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        e.setUsuarioDiretor(diretor);
        return e;
    }
}