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
import model.Nota;
import model.dao.NotaDAO;
import java.util.List;

public class NotaController {

    private NotaDAO notaDAO;

    public NotaController() {
        this.notaDAO = new NotaDAO();
    }

    public String cadastrarNota(Nota nota) {
        if (nota.getNota() < 0 || nota.getNota() > 10) {
            return "A nota deve estar entre 0 e 10.";
        }

        if (nota.getBimestre() < 1 || nota.getBimestre() > 4) {
            return "Bimestre inválido. Use valores de 1 a 4.";
        }

        try {
            notaDAO.inserir(nota);
            return "Nota cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar nota: " + e.getMessage();
        }
    }

    public String atualizarNota(Nota nota) {
        if (nota.getNota() < 0 || nota.getNota() > 10) {
            return "A nota deve estar entre 0 e 10.";
        }

        try {
            notaDAO.atualizar(nota);
            return "Nota atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar nota: " + e.getMessage();
        }
    }

    public String excluirNota(int id) {
        try {
            notaDAO.deletar(id);
            return "Nota excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir nota: " + e.getMessage();
        }
    }

    public Nota buscarNotaPorId(int id) {
        try {
            return notaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nota> listarNotas() {
        try {
            return notaDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}