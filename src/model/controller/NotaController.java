/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.Nota;
import model.Aluno;
import model.dao.NotaDAO;
import model.util.Conexao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NotaController {

    private NotaDAO dao;

    public NotaController() {
        this.dao = new NotaDAO();
    }

    public void salvar(Nota nota) throws SQLException {
        if (nota.getId() == 0) {
            dao.inserir(nota);
        } else {
            dao.atualizar(nota);
        }
    }

    public void excluir(int id) throws SQLException {
        dao.deletar(id);
    }

    public Nota buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Nota> listarTodas() throws SQLException {
        return dao.listarTodas();
    }

    public Map<Aluno, Map<Integer, Double>> getBoletimPorTurmaEDisciplina(int turmaId, int disciplinaId) throws SQLException {
        return dao.getBoletimPorTurmaEDisciplina(turmaId, disciplinaId);
    }
}