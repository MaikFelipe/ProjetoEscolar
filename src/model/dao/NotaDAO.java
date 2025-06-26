/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Nota;
import model.Aluno;
import model.Disciplina;
import model.Turma;
import model.util.Conexao;

public class NotaDAO {

    public void inserir(Nota nota) throws SQLException {
        String sql = "INSERT INTO nota (aluno_id, disciplina_id, turma_id, bimestre, nota) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getAluno().getId());
            stmt.setInt(2, nota.getDisciplina().getId());
            stmt.setInt(3, nota.getTurma().getId());
            stmt.setInt(4, nota.getBimestre());
            stmt.setDouble(5, nota.getNota());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Nota nota) throws SQLException {
        String sql = "UPDATE nota SET aluno_id = ?, disciplina_id = ?, turma_id = ?, bimestre = ?, nota = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getAluno().getId());
            stmt.setInt(2, nota.getDisciplina().getId());
            stmt.setInt(3, nota.getTurma().getId());
            stmt.setInt(4, nota.getBimestre());
            stmt.setDouble(5, nota.getNota());
            stmt.setInt(6, nota.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM nota WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Nota buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM nota WHERE id = ?";
        Nota nota = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nota = new Nota();
                nota.setId(rs.getInt("id"));

                Aluno a = new Aluno();
                a.setId(rs.getInt("aluno_id"));
                nota.setAluno(a);

                Disciplina d = new Disciplina();
                d.setId(rs.getInt("disciplina_id"));
                nota.setDisciplina(d);

                Turma t = new Turma();
                t.setId(rs.getInt("turma_id"));
                nota.setTurma(t);

                nota.setBimestre(rs.getInt("bimestre"));
                nota.setNota(rs.getDouble("nota"));
            }
        }

        return nota;
    }

    public List<Nota> listarTodas() throws SQLException {
        String sql = "SELECT * FROM nota";
        List<Nota> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));

                Aluno a = new Aluno();
                a.setId(rs.getInt("aluno_id"));
                nota.setAluno(a);

                Disciplina d = new Disciplina();
                d.setId(rs.getInt("disciplina_id"));
                nota.setDisciplina(d);

                Turma t = new Turma();
                t.setId(rs.getInt("turma_id"));
                nota.setTurma(t);

                nota.setBimestre(rs.getInt("bimestre"));
                nota.setNota(rs.getDouble("nota"));

                lista.add(nota);
            }
        }

        return lista;
    }
}