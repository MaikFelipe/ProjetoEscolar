/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.CargaHorariaMensalDAO;
import model.CargaHorariaMensal;
import java.sql.SQLException;
import java.util.List;

public class CargaHorariaMensalController {

    private CargaHorariaMensalDAO chmDAO = new CargaHorariaMensalDAO();

    public void cadastrar(CargaHorariaMensal chm) {
        try {
            chmDAO.inserir(chm);
            System.out.println("Carga horária mensal cadastrada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar carga horária: " + e.getMessage());
        }
    }

    public List<CargaHorariaMensal> listarTodos() {
        try {
            return chmDAO.listar();
        } catch (SQLException e) {
            System.err.println("Erro ao listar cargas horárias: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(CargaHorariaMensal chm) {
        try {
            chmDAO.atualizar(chm);
            System.out.println("Carga horária mensal atualizada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carga horária: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            chmDAO.deletar(id);
            System.out.println("Carga horária mensal excluída com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir carga horária: " + e.getMessage());
        }
    }
}