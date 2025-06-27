/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
// CargaHorariaMensalController.java
import model.dao.CargaHorariaMensalDAO;
import model.CargaHorariaMensal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CargaHorariaMensalController {
    private CargaHorariaMensalDAO dao;

    public CargaHorariaMensalController(Connection connection) {
        this.dao = new CargaHorariaMensalDAO(connection);
    }

    public void salvar(CargaHorariaMensal c) throws SQLException {
        if (c.getId() == 0) {
            dao.inserir(c);
        } else {
            dao.atualizar(c);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public CargaHorariaMensal buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<CargaHorariaMensal> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
