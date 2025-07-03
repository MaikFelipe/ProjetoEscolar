/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.EscolaDAO;
import model.Escola;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EscolaController {
    private EscolaDAO dao;

    public EscolaController(Connection connection) {
        this.dao = new EscolaDAO(connection);
    }

    public void salvar(Escola e) throws SQLException {
        if (e.getId() == 0) {
            dao.inserir(e);
        } else {
            dao.atualizar(e);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public Escola buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Escola> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public Escola buscarPorDiretor(int diretorId) throws SQLException {
        return dao.buscarPorDiretor(diretorId);
    }

}