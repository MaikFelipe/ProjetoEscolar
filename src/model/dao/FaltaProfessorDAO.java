/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.FaltaProfessor;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaltaProfessorDAO {
    private Connection connection;

    public FaltaProfessorDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(FaltaProfessor f) throws SQLException {
        String sql = "INSERT INTO falta_professor (professor_id, disciplina_id, data, motivo, documento_anexo, usuario_registro_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, f.getProfessor().getId());
            stmt.setInt(2, f.getDisciplina().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setString(4, f.getMotivo());
            stmt.setString(5, f.getDocumentoAnexo());
            stmt.setInt(6, f.getUsuarioRegistro().getId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(FaltaProfessor f) throws SQLException {
        String sql = "UPDATE falta_professor SET professor_id=?, disciplina_id=?, data=?, motivo=?, documento_anexo=?, usuario_registro_id=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, f.getProfessor().getId());
            stmt.setInt(2, f.getDisciplina().getId());
            stmt.setDate(3, Date.valueOf(f.getData()));
            stmt.setString(4, f.getMotivo());
            stmt.setString(5, f.getDocumentoAnexo());
            stmt.setInt(6, f.getUsuarioRegistro().getId());
            stmt.setInt(7, f.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM falta_professor WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public FaltaProfessor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM falta_professor WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<FaltaProfessor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM falta_professor";
        List<FaltaProfessor> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapear(rs));
        }
        return list;
    }

    private FaltaProfessor mapear(ResultSet rs) throws SQLException {
        FaltaProfessor f = new FaltaProfessor();
        f.setId(rs.getInt("id"));
        model.Professor p = new model.Professor();
        p.setId(rs.getInt("professor_id"));
        f.setProfessor(p);
        model.Disciplina d = new model.Disciplina();
        d.setId(rs.getInt("disciplina_id"));
        f.setDisciplina(d);
        f.setData(rs.getDate("data").toLocalDate());
        f.setMotivo(rs.getString("motivo"));
        f.setDocumentoAnexo(rs.getString("documento_anexo"));
        model.Usuario u = new model.Usuario();
        u.setId(rs.getInt("usuario_registro_id"));
        f.setUsuarioRegistro(u);
        return f;
    }
}