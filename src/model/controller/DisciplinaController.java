/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.DisciplinaDAO;
import model.Disciplina;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisciplinaController {
    private DisciplinaDAO dao;

    public DisciplinaController(Connection connection) {
        this.dao = new DisciplinaDAO(connection);
    }

    public void salvar(Disciplina d) throws SQLException {
        if (d.getId() == 0) {
            dao.inserir(d);
        } else {
            dao.atualizar(d);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public Disciplina buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Disciplina> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}