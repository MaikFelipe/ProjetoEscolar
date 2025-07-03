/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Professor;
import model.Usuario;
import model.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private Connection conn;
    private UsuarioDao usuarioDao;

    public ProfessorDAO(Connection conn) {
        this.conn = conn;
        this.usuarioDao = new UsuarioDao(conn);
    }

    public void cadastrar(Professor p) throws SQLException {
        conn.setAutoCommit(false);
        try {
            usuarioDao.cadastrarUsuario(p.getUsuario());

            String sql = "INSERT INTO professor (usuario_id, disciplina_id, observacoes) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, p.getUsuario().getId());
                if (p.getDisciplina() != null) {
                    stmt.setInt(2, p.getDisciplina().getId());
                } else {
                    stmt.setNull(2, Types.INTEGER);
                }
                stmt.setString(3, p.getObservacoes());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void atualizar(Professor p) throws SQLException {
        conn.setAutoCommit(false);
        try {
            usuarioDao.atualizar(p.getUsuario());

            String sql = "UPDATE professor SET disciplina_id = ?, observacoes = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (p.getDisciplina() != null) {
                    stmt.setInt(1, p.getDisciplina().getId());
                } else {
                    stmt.setNull(1, Types.INTEGER);
                }
                stmt.setString(2, p.getObservacoes());
                stmt.setInt(3, p.getId());
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void excluir(int id) throws SQLException {
        conn.setAutoCommit(false);
        try {
            Professor p = buscarPorId(id);
            if (p == null) throw new SQLException("Professor não encontrado.");

            String sql = "DELETE FROM professor WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            usuarioDao.excluir(p.getUsuario().getId());
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public Professor buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT p.id AS p_id, p.observacoes,
                   u.id AS u_id, u.nome_completo, u.cpf, u.email, u.telefone, u.cargo, u.login, u.nivel_acesso_id, u.senha,
                   d.id AS d_id, d.nome AS d_nome
            FROM professor p
            JOIN usuario u ON p.usuario_id = u.id
            LEFT JOIN disciplina d ON p.disciplina_id = d.id
            WHERE p.id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Professor p = new Professor();
                    p.setId(rs.getInt("p_id"));
                    p.setObservacoes(rs.getString("observacoes"));

                    Usuario u = new Usuario();
                    u.setId(rs.getInt("u_id"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setCpf(rs.getString("cpf"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setCargo(rs.getString("cargo"));
                    u.setLogin(rs.getString("login"));
                    u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                    u.setSenha(rs.getString("senha"));
                    p.setUsuario(u);

                    int dId = rs.getInt("d_id");
                    if (!rs.wasNull()) {
                        Disciplina d = new Disciplina();
                        d.setId(dId);
                        d.setNome(rs.getString("d_nome"));
                        p.setDisciplina(d);
                    }

                    return p;
                }
            }
        }
        return null;
    }

    public List<Professor> listarTodos() throws SQLException {
        List<Professor> lista = new ArrayList<>();
        String sql = """
            SELECT p.id AS p_id, p.observacoes,
                   u.id AS u_id, u.nome_completo, u.cpf, u.email, u.telefone, u.cargo, u.login, u.nivel_acesso_id, u.senha,
                   d.id AS d_id, d.nome AS d_nome
            FROM professor p
            JOIN usuario u ON p.usuario_id = u.id
            LEFT JOIN disciplina d ON p.disciplina_id = d.id
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("p_id"));
                p.setObservacoes(rs.getString("observacoes"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("u_id"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setCpf(rs.getString("cpf"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setCargo(rs.getString("cargo"));
                u.setLogin(rs.getString("login"));
                u.setNivelAcesso(rs.getInt("nivel_acesso_id"));
                u.setSenha(rs.getString("senha"));
                p.setUsuario(u);

                int dId = rs.getInt("d_id");
                if (!rs.wasNull()) {
                    Disciplina d = new Disciplina();
                    d.setId(dId);
                    d.setNome(rs.getString("d_nome"));
                    p.setDisciplina(d);
                }

                lista.add(p);
            }
        }
        return lista;
    }
}
