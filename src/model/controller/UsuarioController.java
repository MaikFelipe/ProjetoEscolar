/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.UsuarioDao;
import model.Usuario;

import java.sql.SQLException;

public class UsuarioController {
    private UsuarioDao usuarioDao;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDao();
    }

    public boolean cadastrarUsuario(Usuario u) {
        try {
            usuarioDao.cadastrarUsuario(u);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro no controller ao cadastrar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticar(String login, String senha) {
        try {
            Usuario usuario = usuarioDao.buscarPorLogin(login);
            if (usuario != null && usuario.getSenha() != null) {
                String senhaCriptografada = model.util.Criptografia.sha256(senha);
                if (senhaCriptografada.equals(usuario.getSenha())) {
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar: " + e.getMessage());
        }
        return null;
    }

    public boolean loginExiste(String login) {
        try {
            return usuarioDao.loginExiste(login);
        } catch (SQLException e) {
            System.err.println("Erro ao verificar login: " + e.getMessage());
            return true;
        }
    }
}