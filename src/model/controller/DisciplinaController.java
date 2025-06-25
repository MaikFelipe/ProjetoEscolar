/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.DisciplinaDAO;
import model.Disciplina;
import java.sql.SQLException;
import java.util.List;

public class DisciplinaController {
    private DisciplinaDAO disciplinaDAO;

    public DisciplinaController() {
        this.disciplinaDAO = new DisciplinaDAO();
    }

    public void adicionarDisciplina(String nome) throws SQLException {
        Disciplina d = new Disciplina();
        d.setNome(nome);
        disciplinaDAO.inserir(d);
    }

    public List<Disciplina> listarDisciplinas() throws SQLException {
        return disciplinaDAO.listar();
    }

    public void atualizarDisciplina(int id, String novoNome) throws SQLException {
        Disciplina d = new Disciplina(id, novoNome);
        disciplinaDAO.atualizar(d);
    }

    public void removerDisciplina(int id) throws SQLException {
        disciplinaDAO.deletar(id);
    }
}
