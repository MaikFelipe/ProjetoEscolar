/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import java.sql.SQLException;
import model.Usuario;
import model.dao.UsuarioDao;

public class UsuarioController {

    private UsuarioDao usuarioDao;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDao();
    }

    public String cadastrarUsuario(Usuario usuario) {
        try {
            usuarioDao.cadastrarUsuario(usuario);
            return "Usuário cadastrado com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar usuário: " + e.getMessage();
        }
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        try {
            return usuarioDao.buscarPorLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verificarSeLoginExiste(String login) {
        try {
            return usuarioDao.loginExiste(login);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}