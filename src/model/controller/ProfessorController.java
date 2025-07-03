/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.controller;

/**
 *
 * @author LASEDi 1781
 */
import model.Professor;
import model.dao.ProfessorDAO;
import model.dao.UsuarioDao;
import model.dao.DisciplinaDAO;
import model.Disciplina;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.util.Conexao;

public class ProfessorController {
    private ProfessorDAO professorDao;
    private UsuarioDao usuarioDao;
    private DisciplinaDAO disciplinaDao;

    public ProfessorController() throws SQLException {
        Connection conn = Conexao.getConexao();
        this.professorDao = new ProfessorDAO(conn);
        this.usuarioDao = new UsuarioDao(conn);
        this.disciplinaDao = new DisciplinaDAO(conn);
    }

    public void cadastrarProfessor(Professor p) throws SQLException {
        if (usuarioDao.loginExiste(p.getUsuario().getLogin())) {
            throw new SQLException("Login já existe.");
        }
        professorDao.cadastrar(p);
    }

    public void atualizarProfessor(Professor p) throws SQLException {
        professorDao.atualizar(p);
    }

    public void excluirProfessor(int id) throws SQLException {
        professorDao.excluir(id);
    }

    public Professor buscarPorId(int id) throws SQLException {
        return professorDao.buscarPorId(id);
    }

    public List<Professor> listarProfessores() throws SQLException {
        return professorDao.listarTodos();
    }

    public List<Disciplina> listarDisciplinas() throws SQLException {
        return disciplinaDao.listarTodos();
    }
}
