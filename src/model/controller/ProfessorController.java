/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.Professor;
import model.dao.ProfessorDAO;
import java.sql.SQLException;
import java.util.List;

public class ProfessorController {

    private ProfessorDAO professorDAO;

    public ProfessorController() {
        this.professorDAO = new ProfessorDAO();
    }

    public String cadastrarProfessor(Professor professor) {
        try {
            professorDAO.inserir(professor);
            return "Professor cadastrado com sucesso.";
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar professor: " + e.getMessage());
            return "Erro ao cadastrar professor: " + e.getMessage();
        }
    }

    public String atualizarProfessor(Professor professor) {
        try {
            professorDAO.atualizar(professor);
            return "Professor atualizado com sucesso.";
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
            return "Erro ao atualizar professor: " + e.getMessage();
        }
    }

    public String excluirProfessor(int id) {
        try {
            professorDAO.excluir(id);
            return "Professor excluído com sucesso.";
        } catch (SQLException e) {
            System.err.println("Erro ao excluir professor: " + e.getMessage());
            return "Erro ao excluir professor: " + e.getMessage();
        }
    }

    public Professor buscarProfessorPorId(int id) {
        try {
            return professorDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor: " + e.getMessage());
            return null;
        }
    }

    public List<Professor> listarProfessores() {
        try {
            return professorDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar professores: " + e.getMessage());
            return null;
        }
    }
}