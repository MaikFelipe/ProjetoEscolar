/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Usuario;
import model.util.Conexao;
import model.util.Criptografia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public boolean loginExiste(String login) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void cadastrarUsuario(Usuario u) throws SQLException {
        if (loginExiste(u.getLogin())) {
            throw new SQLException("Login já está em uso.");
        }

        String sql = "INSERT INTO usuario (nome_completo, cpf, email, telefone, cargo, login, senha, nivel_acesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getCpf());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getTelefone());
            stmt.setString(5, u.getCargo());
            stmt.setString(6, u.getLogin());

            String senhaCriptografada = Criptografia.criptografar(u.getSenha());
            stmt.setString(7, senhaCriptografada);

            stmt.setString(8, u.getNivelAcesso());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuario: " + e.getMessage());
            throw e;
        }
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE login = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setCpf(rs.getString("cpf"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setCargo(rs.getString("cargo"));
                    u.setLogin(rs.getString("login"));
                    u.setNivelAcesso(rs.getString("nivel_acesso"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuario: " + e.getMessage());
            throw e;
        }
        return null;
    }
    
    public List<Usuario> listarPorNivelAcesso(String nivel) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nivel_acesso = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nivel);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setCpf(rs.getString("cpf"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setCargo(rs.getString("cargo"));
                    u.setLogin(rs.getString("login"));
                    u.setNivelAcesso(rs.getString("nivel_acesso"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }
}