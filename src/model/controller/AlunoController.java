/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.dao.AlunoDAO;
import model.Aluno;
import java.sql.SQLException;
import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void cadastrarAluno(Aluno aluno) throws SQLException {
        alunoDAO.inserir(aluno);
    }

    public void atualizarAluno(Aluno aluno) throws SQLException {
        alunoDAO.atualizar(aluno);
    }

    public void excluirAluno(int id) throws SQLException {
        alunoDAO.deletar(id);
    }

    public Aluno buscarAlunoPorId(int id) throws SQLException {
        return alunoDAO.buscarPorId(id);
    }

    public List<Aluno> listarAlunos() throws SQLException {
        return alunoDAO.listarTodos();
    }
}