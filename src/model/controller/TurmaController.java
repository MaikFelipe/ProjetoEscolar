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
import model.Turma;
import model.dao.TurmaDAO;

public class TurmaController {

    private TurmaDAO turmaDAO;

    public TurmaController() {
        this.turmaDAO = new TurmaDAO();
    }

    public String cadastrarTurma(Turma turma) {
        try {
            turmaDAO.inserir(turma);
            return "Turma cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar turma: " + e.getMessage();
        }
    }

    public String atualizarTurma(Turma turma) {
        try {
            turmaDAO.atualizar(turma);
            return "Turma atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar turma: " + e.getMessage();
        }
    }

    public String excluirTurma(int id) {
        try {
            turmaDAO.excluir(id);
            return "Turma excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir turma: " + e.getMessage();
        }
    }

    public Turma buscarTurmaPorId(int id) {
        try {
            return turmaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Turma> listarTurmas() {
        try {
            return turmaDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}