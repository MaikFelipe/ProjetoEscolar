/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.MunicipioDAO;
import model.Municipio;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MunicipioController {
    private MunicipioDAO dao;

    public MunicipioController(Connection connection) {
        this.dao = new MunicipioDAO(connection);
    }

    public void salvar(Municipio m) throws SQLException {
        if (m.getSecretarioEducacao() == null || m.getSecretarioEducacao().getId() == 0) {
            throw new IllegalArgumentException("Secretário de Educação inválido.");
        }

        if (m.getId() == 0) {
            dao.inserir(m);
        } else {
            dao.atualizar(m);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    public Municipio buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Municipio> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}