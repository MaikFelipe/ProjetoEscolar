/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.CalendarioAula;
import model.Turma;
import model.Disciplina;
import model.Usuario;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarioAulaDAO {
    private Connection connection;

    public CalendarioAulaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(CalendarioAula c) throws SQLException {
        String sql = "INSERT INTO calendario_aula (turma_id, disciplina_id, professor_id, dia_semana, horario_inicio, horario_fim) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, c.getTurma().getId());
            stmt.setInt(2, c.getDisciplina().getId());
            stmt.setInt(3, c.getProfessor().getId());
            stmt.setString(4, c.getDiaSemana());
            stmt.setTime(5, Time.valueOf(c.getHorarioInicio()));
            stmt.setTime(6, Time.valueOf(c.getHorarioFim()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(CalendarioAula c) throws SQLException {
        String sql = "UPDATE calendario_aula SET turma_id=?, disciplina_id=?, professor_id=?, dia_semana=?, horario_inicio=?, horario_fim=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, c.getTurma().getId());
            stmt.setInt(2, c.getDisciplina().getId());
            stmt.setInt(3, c.getProfessor().getId());
            stmt.setString(4, c.getDiaSemana());
            stmt.setTime(5, Time.valueOf(c.getHorarioInicio()));
            stmt.setTime(6, Time.valueOf(c.getHorarioFim()));
            stmt.setInt(7, c.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM calendario_aula WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public CalendarioAula buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM calendario_aula WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCalendarioAula(rs);
                }
            }
        }
        return null;
    }

    public List<CalendarioAula> listarTodos() throws SQLException {
        String sql = "SELECT * FROM calendario_aula";
        List<CalendarioAula> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapearCalendarioAula(rs));
            }
        }
        return list;
    }

    public List<CalendarioAula> listarPorProfessor(int idProfessor) throws SQLException {
        String sql = """
            SELECT ca.id, ca.turma_id, ca.disciplina_id, ca.professor_id, ca.dia_semana, ca.horario_inicio, ca.horario_fim,
                   t.nome AS turma_nome, t.serie AS turma_serie, t.nivel_ensino AS turma_nivel_ensino, t.ano_letivo AS turma_ano_letivo, t.turno AS turma_turno,
                   d.nome AS disciplina_nome
            FROM calendario_aula ca
            JOIN turma t ON ca.turma_id = t.id
            JOIN disciplina d ON ca.disciplina_id = d.id
            WHERE ca.professor_id = ?
            ORDER BY t.nome, d.nome
            """;
        List<CalendarioAula> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProfessor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CalendarioAula c = new CalendarioAula();
                    c.setId(rs.getInt("id"));

                    Turma t = new Turma();
                    t.setId(rs.getInt("turma_id"));
                    t.setNome(rs.getString("turma_nome"));
                    t.setSerie(rs.getString("turma_serie"));
                    t.setNivelEnsino(rs.getString("turma_nivel_ensino"));
                    t.setAnoLetivo(rs.getInt("turma_ano_letivo"));
                    t.setTurno(rs.getString("turma_turno"));
                    c.setTurma(t);

                    Disciplina d = new Disciplina();
                    d.setId(rs.getInt("disciplina_id"));
                    d.setNome(rs.getString("disciplina_nome"));
                    c.setDisciplina(d);

                    Usuario u = new Usuario();
                    u.setId(rs.getInt("professor_id"));
                    c.setProfessor(u);

                    c.setDiaSemana(rs.getString("dia_semana"));
                    c.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                    c.setHorarioFim(rs.getTime("horario_fim").toLocalTime());

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    private CalendarioAula mapearCalendarioAula(ResultSet rs) throws SQLException {
        CalendarioAula c = new CalendarioAula();
        c.setId(rs.getInt("id"));

        Turma t = new Turma();
        t.setId(rs.getInt("turma_id"));
        c.setTurma(t);

        Disciplina d = new Disciplina();
        d.setId(rs.getInt("disciplina_id"));
        c.setDisciplina(d);

        Usuario u = new Usuario();
        u.setId(rs.getInt("professor_id"));
        c.setProfessor(u);

        c.setDiaSemana(rs.getString("dia_semana"));
        c.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
        c.setHorarioFim(rs.getTime("horario_fim").toLocalTime());
        return c;
    }
}