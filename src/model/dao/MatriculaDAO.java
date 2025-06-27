/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Matricula;
import model.Aluno;
import model.Turma;
import model.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    public void inserir(Matricula matricula) throws SQLException {
        String sql = "INSERT INTO matricula (aluno_id, turma_id, data_matricula, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getAluno().getId());
            stmt.setInt(2, matricula.getTurma().getId());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setString(4, matricula.getStatus());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Matricula matricula) throws SQLException {
        String sql = "UPDATE matricula SET aluno_id = ?, turma_id = ?, data_matricula = ?, status = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getAluno().getId());
            stmt.setInt(2, matricula.getTurma().getId());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setString(4, matricula.getStatus());
            stmt.setInt(5, matricula.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Matricula buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id = ?";
        Matricula matricula = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                matricula = new Matricula();
                matricula.setId(rs.getInt("id"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                matricula.setAluno(aluno);

                Turma turma = new Turma();
                turma.setId(rs.getInt("turma_id"));
                matricula.setTurma(turma);

                matricula.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
                matricula.setStatus(rs.getString("status"));
            }
        }

        return matricula;
    }

    public List<Matricula> listarTodas() throws SQLException {
        String sql = "SELECT * FROM matricula";
        List<Matricula> lista = new ArrayList<>();

        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            AlunoDAO alunoDAO = new AlunoDAO(conn);
            TurmaDAO turmaDAO = new TurmaDAO();

            while (rs.next()) {
                Matricula m = new Matricula();
                m.setId(rs.getInt("id"));
                m.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
                m.setStatus(rs.getString("status"));

                int alunoId = rs.getInt("aluno_id");
                int turmaId = rs.getInt("turma_id");

                Aluno aluno = alunoDAO.buscarPorId(alunoId);
                Turma turma = turmaDAO.buscarPorId(turmaId);

                m.setAluno(aluno);
                m.setTurma(turma);

                lista.add(m);
            }
        }

        return lista;
    }

}