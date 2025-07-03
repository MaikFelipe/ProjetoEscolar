/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.FaltaProfessorDAO;
import model.FaltaProfessor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FaltaProfessorController {
    private FaltaProfessorDAO dao;

    public FaltaProfessorController(Connection connection) {
        this.dao = new FaltaProfessorDAO(connection);
    }

    public void salvar(FaltaProfessor f) throws SQLException {
        if (f.getId() == 0) dao.inserir(f);
        else dao.atualizar(f);
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public FaltaProfessor buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<FaltaProfessor> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}