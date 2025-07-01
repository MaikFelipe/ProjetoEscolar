/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.FrequenciaDAO;
import model.Frequencia;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FrequenciaController {
    private FrequenciaDAO dao;

    public FrequenciaController(Connection connection) {
        this.dao = new FrequenciaDAO(connection);
    }

    public void salvar(Frequencia f) throws SQLException {
        if (f.getId() == 0) dao.inserir(f);
        else dao.atualizar(f);
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public Frequencia buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Frequencia> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}