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
import model.Escola;
import model.dao.EscolaDAO;

public class EscolaController {

    private EscolaDAO escolaDAO;

    public EscolaController() {
        this.escolaDAO = new EscolaDAO();
    }

    public String cadastrarEscola(Escola escola) {
        try {
            escolaDAO.inserir(escola);
            return "Escola cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar escola: " + e.getMessage();
        }
    }

    public String atualizarEscola(Escola escola) {
        try {
            escolaDAO.atualizar(escola);
            return "Escola atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar escola: " + e.getMessage();
        }
    }

    public String deletarEscola(int id) {
        try {
            escolaDAO.deletar(id);
            return "Escola deletada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao deletar escola: " + e.getMessage();
        }
    }

    public Escola buscarEscolaPorId(int id) {
        try {
            return escolaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Escola> listarEscolas() {
        try {
            return escolaDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}