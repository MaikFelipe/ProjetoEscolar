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
import model.Matricula;
import model.dao.MatriculaDAO;

public class MatriculaController {

    private MatriculaDAO matriculaDAO;

    public MatriculaController() {
        this.matriculaDAO = new MatriculaDAO();
    }

    public String cadastrarMatricula(Matricula matricula) {
        try {
            matriculaDAO.inserir(matricula);
            return "Matrícula cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar matrícula: " + e.getMessage();
        }
    }

    public String atualizarMatricula(Matricula matricula) {
        try {
            matriculaDAO.atualizar(matricula);
            return "Matrícula atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar matrícula: " + e.getMessage();
        }
    }

    public String excluirMatricula(int id) {
        try {
            matriculaDAO.excluir(id);
            return "Matrícula excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir matrícula: " + e.getMessage();
        }
    }

    public Matricula buscarMatriculaPorId(int id) {
        try {
            return matriculaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Matricula> listarMatriculas() {
        try {
            return matriculaDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}