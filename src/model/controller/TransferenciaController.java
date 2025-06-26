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
import model.Transferencia;
import model.dao.TransferenciaDAO;

public class TransferenciaController {

    private TransferenciaDAO transferenciaDAO;

    public TransferenciaController() {
        this.transferenciaDAO = new TransferenciaDAO();
    }

    public String cadastrarTransferencia(Transferencia transferencia) {
        try {
            transferenciaDAO.inserir(transferencia);
            return "Transferência cadastrada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar transferência: " + e.getMessage();
        }
    }

    public String atualizarTransferencia(Transferencia transferencia) {
        try {
            transferenciaDAO.atualizar(transferencia);
            return "Transferência atualizada com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar transferência: " + e.getMessage();
        }
    }

    public String excluirTransferencia(int id) {
        try {
            transferenciaDAO.deletar(id);
            return "Transferência excluída com sucesso.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir transferência: " + e.getMessage();
        }
    }

    public Transferencia buscarTransferenciaPorId(int id) {
        try {
            return transferenciaDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transferencia> listarTransferencias() {
        try {
            return transferenciaDAO.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}