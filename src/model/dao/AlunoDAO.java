/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author LASEDi 1781
 */
import model.Aluno;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection connection;

    public AlunoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (nome_completo, data_nascimento, cpf, endereco, telefone, nome_responsavel, cpf_responsavel, email_responsavel, telefone_responsavel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getEnderecoCompleto());
            stmt.setString(5, aluno.getTelefone());
            stmt.setString(6, aluno.getNomeResponsavel());
            stmt.setString(7, aluno.getCpfResponsavel());
            stmt.setString(8, aluno.getEmailResponsavel());
            stmt.setString(9, aluno.getTelefoneResponsavel());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) aluno.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET nome_completo=?, data_nascimento=?, cpf=?, endereco=?, telefone=?, nome_responsavel=?, cpf_responsavel=?, email_responsavel=?, telefone_responsavel=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getEnderecoCompleto());
            stmt.setString(5, aluno.getTelefone());
            stmt.setString(6, aluno.getNomeResponsavel());
            stmt.setString(7, aluno.getCpfResponsavel());
            stmt.setString(8, aluno.getEmailResponsavel());
            stmt.setString(9, aluno.getTelefoneResponsavel());
            stmt.setInt(10, aluno.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Aluno buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAluno(rs);
                }
            }
        }
        return null;
    }

    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT * FROM aluno";
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }
        }
        return alunos;
    }

    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setNomeCompleto(rs.getString("nome_completo"));
        a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        a.setCpf(rs.getString("cpf"));
        a.setEnderecoCompleto(rs.getString("endereco"));
        a.setTelefone(rs.getString("telefone"));
        a.setNomeResponsavel(rs.getString("nome_responsavel"));
        a.setCpfResponsavel(rs.getString("cpf_responsavel"));
        a.setEmailResponsavel(rs.getString("email_responsavel"));
        a.setTelefoneResponsavel(rs.getString("telefone_responsavel"));
        return a;
    }
}