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
import model.Escola;
import model.Municipio;
import model.Usuario;
import model.util.Conexao;

public class EscolaDAO {

    public void inserir(Escola escola) throws SQLException {
        String sql = "INSERT INTO escola (nome, endereco, bairro, municipio_id, telefone, email, diretor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEnderecoCompleto());
            stmt.setString(3, escola.getBairro());
            stmt.setInt(4, escola.getMunicipio().getId());
            stmt.setString(5, escola.getTelefone());
            stmt.setString(6, escola.getEmail());
            stmt.setInt(7, escola.getUsuarioDiretor().getId());

            stmt.executeUpdate();
        }
    }

    public Escola buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM escola WHERE id = ?";
        Escola escola = null;

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                escola = new Escola();
                escola.setId(rs.getInt("id"));
                escola.setNome(rs.getString("nome"));
                escola.setEnderecoCompleto(rs.getString("endereco"));
                escola.setBairro(rs.getString("bairro"));

                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("municipio_id"));
                escola.setMunicipio(municipio);

                escola.setTelefone(rs.getString("telefone"));
                escola.setEmail(rs.getString("email"));

                Usuario diretor = new Usuario();
                diretor.setId(rs.getInt("diretor_id"));
                escola.setUsuarioDiretor(diretor);
            }
        }

        return escola;
    }

    public List<Escola> listarTodas() throws SQLException {
        String sql = "SELECT * FROM escola";
        List<Escola> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Escola escola = new Escola();
                escola.setId(rs.getInt("id"));
                escola.setNome(rs.getString("nome"));
                escola.setEnderecoCompleto(rs.getString("endereco"));
                escola.setBairro(rs.getString("bairro"));

                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("municipio_id"));
                escola.setMunicipio(municipio);

                escola.setTelefone(rs.getString("telefone"));
                escola.setEmail(rs.getString("email"));

                Usuario diretor = new Usuario();
                diretor.setId(rs.getInt("diretor_id"));
                escola.setUsuarioDiretor(diretor);

                lista.add(escola);
            }
        }

        return lista;
    }

    public void atualizar(Escola escola) throws SQLException {
        String sql = "UPDATE escola SET nome = ?, endereco = ?, bairro = ?, municipio_id = ?, telefone = ?, email = ?, diretor_id = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEnderecoCompleto());
            stmt.setString(3, escola.getBairro());
            stmt.setInt(4, escola.getMunicipio().getId());
            stmt.setString(5, escola.getTelefone());
            stmt.setString(6, escola.getEmail());
            stmt.setInt(7, escola.getUsuarioDiretor().getId());
            stmt.setInt(8, escola.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM escola WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}