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

    public boolean loginExiste(String login) throws SQLException {
        return usuarioDao.loginExiste(login);
    }

    public void cadastrarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getLogin() == null || usuario.getLogin().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }
        usuarioDao.cadastrarUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido");
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

    public List<Usuario> listarPorNivelAcesso(String nivel) throws SQLException {
        return usuarioDao.listarPorNivelAcesso(nivel);
    }
}