/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.Usuario;
import model.dao.UsuarioDao;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private UsuarioDao usuarioDao;

    public UsuarioController() {
        usuarioDao = new UsuarioDao();
    }

    public void cadastrarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getNomeCompleto().isEmpty() || usuario.getCpf().isEmpty() || usuario.getEmail().isEmpty()
                || usuario.getTelefone().isEmpty() || usuario.getCargo().isEmpty()
                || usuario.getLogin().isEmpty() || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
        }
        usuarioDao.cadastrarUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getNomeCompleto().isEmpty() || usuario.getCpf().isEmpty() || usuario.getEmail().isEmpty()
                || usuario.getTelefone().isEmpty() || usuario.getCargo().isEmpty()
                || usuario.getLogin().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
        }
        usuarioDao.atualizar(usuario);
    }

    public void excluirUsuario(int id) throws SQLException {
        usuarioDao.excluir(id);
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        return usuarioDao.buscarPorLogin(login);
    }

    public Usuario buscarPorId(int id) throws SQLException {
        return usuarioDao.buscarPorId(id);
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDao.listarTodos();
    }

    public List<Usuario> listarPorNivelAcesso(int nivel) throws SQLException {
        return usuarioDao.listarPorNivelAcesso(String.valueOf(nivel));
    }
    
    public Usuario autenticar(String login, String senhaCriptografada) throws SQLException {
        return usuarioDao.autenticar(login, senhaCriptografada);
    }
}