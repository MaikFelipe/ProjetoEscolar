/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.MatriculaDAO;
import model.Matricula;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MatriculaController {
    private MatriculaDAO dao;

    public MatriculaController(Connection connection) {
        this.dao = new MatriculaDAO(connection);
    }

    public void salvar(Matricula m) throws SQLException {
        if (m.getId() == 0) dao.inserir(m);
        else dao.atualizar(m);
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public Matricula buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Matricula> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}