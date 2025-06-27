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
import model.dao.FrequenciaDAO;
import model.Frequencia;
import java.util.List;

public class FrequenciaController {

    private FrequenciaDAO frequenciaDAO;

    public FrequenciaController() {
        this.frequenciaDAO = new FrequenciaDAO();
    }

    public String cadastrarFrequencia(Frequencia f) {
        try {
            frequenciaDAO.inserir(f);
            return "Frequência cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar frequência: " + e.getMessage();
        }
    }

    public String atualizarFrequencia(Frequencia f) {
        try {
            frequenciaDAO.atualizar(f);
            return "Frequência atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar frequência: " + e.getMessage();
        }
    }

    public String excluirFrequencia(int id) {
        try {
            frequenciaDAO.deletar(id);
            return "Frequência excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir frequência: " + e.getMessage();
        }
    }

    public Frequencia buscarFrequenciaPorId(int id) {
        try {
            return frequenciaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Frequencia> listarFrequenciasPorAluno(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}