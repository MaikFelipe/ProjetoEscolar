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
import java.util.List;
import model.Municipio;
import model.dao.MunicipioDAO;

public class MunicipioController {

    private MunicipioDAO municipioDAO;

    public MunicipioController() {
        this.municipioDAO = new MunicipioDAO();
    }

    public String cadastrarMunicipio(Municipio m) {
        try {
            municipioDAO.inserir(m);
            return "Município cadastrado com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar município: " + e.getMessage();
        }
    }

    public String atualizarMunicipio(Municipio m) {
        try {
            municipioDAO.atualizar(m);
            return "Município atualizado com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar município: " + e.getMessage();
        }
    }

    public String excluirMunicipio(int id) {
        try {
            municipioDAO.deletar(id);
            return "Município excluído com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir município: " + e.getMessage();
        }
    }

    public Municipio buscarMunicipioPorId(int id) {
        try {
            return municipioDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Municipio> listarMunicipios() {
        try {
            return municipioDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}