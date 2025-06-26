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
import model.FaltaProfessor;
import model.Professor;
import model.Disciplina;
import model.Usuario;
import model.util.Conexao;

public class FaltaProfessorDAO {

    public void inserir(FaltaProfessor falta) throws SQLException {
        String sql = "INSERT INTO falta_professor (professor_id, disciplina_id, data, motivo, documento_anexo, usuario_registro_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, falta.getProfessor().getId());
            stmt.setInt(2, falta.getDisciplina().getId());
            stmt.setDate(3, Date.valueOf(falta.getData()));
            stmt.setString(4, falta.getMotivo());
            stmt.setString(5, falta.getDocumentoAnexo());
            stmt.setInt(6, falta.getUsuarioRegistro().getId());

            stmt.executeUpdate();
        }
    }

    public void atualizar(FaltaProfessor falta) throws SQLException {
        String sql = "UPDATE falta_professor SET professor_id=?, disciplina_id=?, data=?, motivo=?, documento_anexo=?, usuario_registro_id=? WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, falta.getProfessor().getId());
            stmt.setInt(2, falta.getDisciplina().getId());
            stmt.setDate(3, Date.valueOf(falta.getData()));
            stmt.setString(4, falta.getMotivo());
            stmt.setString(5, falta.getDocumentoAnexo());
            stmt.setInt(6, falta.getUsuarioRegistro().getId());
            stmt.setInt(7, falta.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM falta_professor WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public FaltaProfessor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM falta_professor WHERE id=?";
        FaltaProfessor falta = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                falta = new FaltaProfessor();
                falta.setId(rs.getInt("id"));

                Professor p = new Professor();
                p.setId(rs.getInt("professor_id"));
                falta.setProfessor(p);

                Disciplina d = new Disciplina();
                d.setId(rs.getInt("disciplina_id"));
                falta.setDisciplina(d);

                falta.setData(rs.getDate("data").toLocalDate());
                falta.setMotivo(rs.getString("motivo"));
                falta.setDocumentoAnexo(rs.getString("documento_anexo"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_registro_id"));
                falta.setUsuarioRegistro(u);
            }
        }

        return falta;
    }

    public List<FaltaProfessor> listarTodas() throws SQLException {
        String sql = "SELECT * FROM falta_professor";
        List<FaltaProfessor> faltas = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FaltaProfessor falta = new FaltaProfessor();
                falta.setId(rs.getInt("id"));

                Professor p = new Professor();
                p.setId(rs.getInt("professor_id"));
                falta.setProfessor(p);

                Disciplina d = new Disciplina();
                d.setId(rs.getInt("disciplina_id"));
                falta.setDisciplina(d);

                falta.setData(rs.getDate("data").toLocalDate());
                falta.setMotivo(rs.getString("motivo"));
                falta.setDocumentoAnexo(rs.getString("documento_anexo"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_registro_id"));
                falta.setUsuarioRegistro(u);

                faltas.add(falta);
            }
        }

        return faltas;
    }
}