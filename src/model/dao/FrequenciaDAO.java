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
import model.Frequencia;
import model.Aluno;
import model.Turma;
import model.util.Conexao;

public class FrequenciaDAO {

    public void inserir(Frequencia f) throws SQLException {
        String sql = "INSERT INTO frequencia (aluno_id, turma_id, data, presente, total_faltas_acumuladas) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getAluno().getId());
            stmt.setInt(2, f.getTurma().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setBoolean(4, f.isPresente());
            stmt.setInt(5, f.getTotalFaltasAcumuladas());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Frequencia f) throws SQLException {
        String sql = "UPDATE frequencia SET aluno_id=?, turma_id=?, data=?, presente=?, total_faltas_acumuladas=? WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getAluno().getId());
            stmt.setInt(2, f.getTurma().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setBoolean(4, f.isPresente());
            stmt.setInt(5, f.getTotalFaltasAcumuladas());
            stmt.setInt(6, f.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM frequencia WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Frequencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM frequencia WHERE id=?";
        Frequencia f = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                f = new Frequencia();
                f.setId(rs.getInt("id"));

                Aluno a = new Aluno();
                a.setId(rs.getInt("aluno_id"));
                f.setAluno(a);

                Turma t = new Turma();
                t.setId(rs.getInt("turma_id"));
                f.setTurma(t);

                f.setData(rs.getDate("data").toLocalDate());
                f.setPresente(rs.getBoolean("presente"));
                f.setTotalFaltasAcumuladas(rs.getInt("total_faltas_acumuladas"));
            }
        }

        return f;
    }

    public List<Frequencia> listarTodas() throws SQLException {
        String sql = "SELECT * FROM frequencia";
        List<Frequencia> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Frequencia f = new Frequencia();
                f.setId(rs.getInt("id"));

                Aluno a = new Aluno();
                a.setId(rs.getInt("aluno_id"));
                f.setAluno(a);

                Turma t = new Turma();
                t.setId(rs.getInt("turma_id"));
                f.setTurma(t);

                f.setData(rs.getDate("data").toLocalDate());
                f.setPresente(rs.getBoolean("presente"));
                f.setTotalFaltasAcumuladas(rs.getInt("total_faltas_acumuladas"));

                lista.add(f);
            }
        }

        return lista;
    }
}