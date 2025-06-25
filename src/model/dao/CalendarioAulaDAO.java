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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.CalendarioAula;
import model.Disciplina;
import model.Turma;
import model.Usuario;
import model.util.Conexao;

public class CalendarioAulaDAO {
    
    public void inserir(CalendarioAula ca) throws SQLException {
        String sql = "INSERT INTO calendario_aula (turma_id, disciplina_id, professor_id, dia_semana, horario_inicio, horario_fim) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ca.getTurma().getId());
            stmt.setInt(2, ca.getDisciplina().getId());
            stmt.setInt(3, ca.getProfessor().getId());
            stmt.setString(4, ca.getDiaSemana());
            stmt.setTime(5, Time.valueOf(ca.getHorarioInicio()));
            stmt.setTime(6, Time.valueOf(ca.getHorarioFim()));
            stmt.executeUpdate();
        }
    }

    public List<CalendarioAula> listar() throws SQLException {
        List<CalendarioAula> lista = new ArrayList<>();
        String sql = "SELECT * FROM calendario_aula";
        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CalendarioAula ca = new CalendarioAula();
                ca.setId(rs.getInt("id"));

                Turma turma = new Turma();
                turma.setId(rs.getInt("turma_id"));
                ca.setTurma(turma);

                Usuario professor = new Usuario();
                professor.setId(rs.getInt("professor_id"));
                ca.setProfessor(professor);

                Disciplina d = new Disciplina();
                ca.setId(rs.getInt("disciplina_id"));
                ca.setDiaSemana(rs.getString("dia_semana"));
                ca.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                ca.setHorarioFim(rs.getTime("horario_fim").toLocalTime());


                lista.add(ca);
            }
        }
        return lista;
    }

    public void atualizar(CalendarioAula ca) throws SQLException {
        String sql = "UPDATE calendario_aula SET turma_id=?, disciplina_id=?, professor_id=?, dia_semana=?, horario_inicio=?, horario_fim=? WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ca.getTurma().getId());
            stmt.setInt(2, ca.getDisciplina().getId());
            stmt.setInt(3, ca.getProfessor().getId());
            stmt.setString(4, ca.getDiaSemana());
            stmt.setTime(5, Time.valueOf(ca.getHorarioInicio()));
            stmt.setTime(6, Time.valueOf(ca.getHorarioFim()));
            stmt.setInt(7, ca.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM calendario_aula WHERE id=?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}