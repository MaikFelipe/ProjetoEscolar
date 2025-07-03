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
import model.Transferencia;
import model.Aluno;
import model.Escola;
import model.util.Conexao;

public class TransferenciaDAO {

    public void inserir(Transferencia t) throws SQLException {
        String sql = "INSERT INTO transferencia (aluno_id, escola_origem_id, escola_destino_id, data_solicitacao, status, notas_migradas) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getAluno().getId());
            stmt.setInt(2, t.getEscolaOrigem().getId());
            stmt.setInt(3, t.getEscolaDestino().getId());
            stmt.setDate(4, Date.valueOf(t.getDataSolicitacao())); // conversão de LocalDate
            stmt.setString(5, t.getStatus());
            stmt.setBoolean(6, t.isNotasMigradas());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Transferencia t) throws SQLException {
        String sql = "UPDATE transferencia SET aluno_id=?, escola_origem_id=?, escola_destino_id=?, data_solicitacao=?, status=?, notas_migradas=? WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getAluno().getId());
            stmt.setInt(2, t.getEscolaOrigem().getId());
            stmt.setInt(3, t.getEscolaDestino().getId());
            stmt.setDate(4, Date.valueOf(t.getDataSolicitacao()));
            stmt.setString(5, t.getStatus());
            stmt.setBoolean(6, t.isNotasMigradas());
            stmt.setInt(7, t.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM transferencia WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Transferencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM transferencia WHERE id=?";
        Transferencia t = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = new Transferencia();
                t.setId(rs.getInt("id"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                t.setAluno(aluno);

                Escola origem = new Escola();
                origem.setId(rs.getInt("escola_origem_id"));
                t.setEscolaOrigem(origem);

                Escola destino = new Escola();
                destino.setId(rs.getInt("escola_destino_id"));
                t.setEscolaDestino(destino);

                t.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                t.setStatus(rs.getString("status"));
                t.setNotasMigradas(rs.getBoolean("notas_migradas"));
            }
        }

        return t;
    }

    public List<Transferencia> listarTodas() throws SQLException {
        String sql = "SELECT * FROM transferencia";
        List<Transferencia> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transferencia t = new Transferencia();
                t.setId(rs.getInt("id"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                t.setAluno(aluno);

                Escola origem = new Escola();
                origem.setId(rs.getInt("escola_origem_id"));
                t.setEscolaOrigem(origem);

                Escola destino = new Escola();
                destino.setId(rs.getInt("escola_destino_id"));
                t.setEscolaDestino(destino);

                t.setDataSolicitacao(rs.getDate("data_solicitacao").toLocalDate());
                t.setStatus(rs.getString("status"));
                t.setNotasMigradas(rs.getBoolean("notas_migradas"));

                lista.add(t);
            }
        }

        return lista;
    }
}