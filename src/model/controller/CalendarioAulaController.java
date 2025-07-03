/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.CalendarioAulaDAO;
import model.CalendarioAula;
import model.util.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CalendarioAulaController {
    private CalendarioAulaDAO dao;

    // Construtor sem parâmetros que cria a conexão automaticamente
    public CalendarioAulaController() {
        try {
            Connection connection = Conexao.getConexao();
            this.dao = new CalendarioAulaDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }

    // Seu construtor atual
    public CalendarioAulaController(Connection connection) {
        this.dao = new CalendarioAulaDAO(connection);
    }

    public void salvar(CalendarioAula c) throws SQLException {
        if (c.getId() == 0) {
            dao.inserir(c);
        } else {
            dao.atualizar(c);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public CalendarioAula buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<CalendarioAula> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public List<CalendarioAula> listarPorProfessor(int idProfessor) throws SQLException {
        return dao.listarPorProfessor(idProfessor);
    }
}