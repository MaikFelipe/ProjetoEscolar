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
import model.FaltaProfessor;
import model.dao.FaltaProfessorDAO;

public class FaltaProfessorController {

    private FaltaProfessorDAO faltaProfessorDAO;

    public FaltaProfessorController() {
        this.faltaProfessorDAO = new FaltaProfessorDAO();
    }

    public String cadastrarFalta(FaltaProfessor falta) {
        try {
            faltaProfessorDAO.inserir(falta);
            return "Falta registrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao registrar falta: " + e.getMessage();
        }
    }

    public String atualizarFalta(FaltaProfessor falta) {
        try {
            faltaProfessorDAO.atualizar(falta);
            return "Falta atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar falta: " + e.getMessage();
        }
    }

    public String excluirFalta(int id) {
        try {
            faltaProfessorDAO.deletar(id);
            return "Falta excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir falta: " + e.getMessage();
        }
    }

    public FaltaProfessor buscarFaltaPorId(int id) {
        try {
            return faltaProfessorDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FaltaProfessor> listarFaltas() {
        try {
            return faltaProfessorDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}