/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.Aluno;
import model.dao.AlunoDAO;
import model.util.Conexao;

import java.sql.SQLException;
import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        try {
            this.alunoDAO = new AlunoDAO(Conexao.getConexao());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão no AlunoController: " + e.getMessage(), e);
        }
    }

    public void salvarAluno(Aluno aluno) throws SQLException {
        if (aluno.getId() == 0) {
            alunoDAO.inserir(aluno);
        } else {
            alunoDAO.atualizar(aluno);
        }
    }

    public void deletarAluno(int id) throws SQLException {
        alunoDAO.excluir(id);
    }

    public Aluno buscarAluno(int id) throws SQLException {
        return alunoDAO.buscarPorId(id);
    }

    public List<Aluno> listarAlunos() throws SQLException {
        return alunoDAO.listarTodos();
    }
}
