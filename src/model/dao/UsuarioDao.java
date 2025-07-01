/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 * **/
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

        String sql = "INSERT INTO usuario (nome_completo, cpf, email, telefone, cargo, login, senha, nivel_acesso_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getCpf());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getTelefone());
            stmt.setString(5, u.getCargo());
            stmt.setString(6, u.getLogin());

            String senhaCriptografada = Criptografia.criptografar(u.getSenha());
            stmt.setString(7, senhaCriptografada);

            stmt.setInt(8, u.getNivelAcesso());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nome_completo = ?, cpf = ?, email = ?, telefone = ?, cargo = ?, login = ?, senha = ?, nivel_acesso_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getCpf());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getTelefone());
            stmt.setString(5, u.getCargo());
            stmt.setString(6, u.getLogin());

            String senhaCriptografada = Criptografia.criptografar(u.getSenha());
            stmt.setString(7, senhaCriptografada);

            stmt.setInt(8, u.getNivelAcesso());
            stmt.setInt(9, u.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
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
                    u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                    u.setSenha(rs.getString("senha"));
                    return u;
                }
            }
        }
        return null;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
                    u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                    u.setSenha(rs.getString("senha"));
                    return u;
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setCpf(rs.getString("cpf"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setCargo(rs.getString("cargo"));
                u.setLogin(rs.getString("login"));
                u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                lista.add(u);
            }
        }
        return lista;
    }

    public List<Usuario> listarPorNivelAcesso(String nivel) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nivel_acesso_id = ?";
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
                    u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }
    
    public Usuario autenticar(String login, String senhaCriptografada) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senhaCriptografada);
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
                    u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                    u.setSenha(rs.getString("senha"));
                    return u;
                }
            }
        }
        return null;
    }
}