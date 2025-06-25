/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.CalendarioAulaDAO;
import model.CalendarioAula;
import java.sql.SQLException;
import java.util.List;

public class CalendarioAulaController {
    private CalendarioAulaDAO caDAO = new CalendarioAulaDAO();

    public void cadastrar(CalendarioAula ca) {
        try {
            caDAO.inserir(ca);
            System.out.println("Calendário de aula cadastrado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar calendário: " + e.getMessage());
        }
    }

    public List<CalendarioAula> listarTodos() {
        try {
            return caDAO.listar();
        } catch (SQLException e) {
            System.err.println("Erro ao listar calendários: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(CalendarioAula ca) {
        try {
            caDAO.atualizar(ca);
            System.out.println("Calendário de aula atualizado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar calendário: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            caDAO.deletar(id);
            System.out.println("Calendário de aula excluido com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir calendário: " + e.getMessage());
        }
    }
}