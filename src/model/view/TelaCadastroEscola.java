/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Escola;
import model.Municipio;
import model.Usuario;
import model.controller.EscolaController;
import model.dao.MunicipioDAO;
import model.dao.UsuarioDao;
import model.util.Conexao;

public class TelaCadastroEscola extends JFrame {

    private JTextField tfNome, tfEndereco, tfBairro, tfTelefone, tfEmail;
    private JComboBox<Municipio> cbMunicipio;
    private JComboBox<Usuario> cbDiretor;
    private JButton btnCadastrar, btnVoltar;
    private EscolaController escolaController;
    private Connection connection;

    public TelaCadastroEscola() {
        try {
            connection = Conexao.getConexao();
            escolaController = new EscolaController(connection);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        configurarJanela();
        criarComponentes();
        carregarMunicipios();
        carregarDiretores();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Cadastro de Escola");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void criarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        tfNome = new JTextField(25);
        tfEndereco = new JTextField(25);
        tfBairro = new JTextField(15);
        tfTelefone = new JTextField(15);
        tfEmail = new JTextField(25);
        cbMunicipio = new JComboBox<>();
        cbDiretor = new JComboBox<>();
        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrarEscola());
        btnVoltar.addActionListener(e -> dispose());

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; painel.add(tfNome, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1; painel.add(tfEndereco, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Bairro:"), gbc);
        gbc.gridx = 1; painel.add(tfBairro, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; painel.add(tfTelefone, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; painel.add(tfEmail, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Município:"), gbc);
        gbc.gridx = 1; painel.add(cbMunicipio, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Diretor:"), gbc);
        gbc.gridx = 1; painel.add(cbDiretor, gbc); y++;

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        add(painel, BorderLayout.CENTER);
    }

    private void carregarMunicipios() {
        try {
            MunicipioDAO municipioDAO = new MunicipioDAO();
            List<Municipio> municipios = municipioDAO.listarTodos();
            cbMunicipio.removeAllItems();
            for (Municipio m : municipios) {
                cbMunicipio.addItem(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar municípios: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDiretores() {
        try {
            UsuarioDao dao = new UsuarioDao();
            List<Usuario> diretores = dao.listarPorNivelAcesso("Diretor");
            cbDiretor.removeAllItems();
            for (Usuario u : diretores) {
                cbDiretor.addItem(u);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar diretores: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarEscola() {
        String nome = tfNome.getText().trim();
        String endereco = tfEndereco.getText().trim();
        String bairro = tfBairro.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String email = tfEmail.getText().trim();
        Municipio municipio = (Municipio) cbMunicipio.getSelectedItem();
        Usuario diretor = (Usuario) cbDiretor.getSelectedItem();

        if (nome.isEmpty() || endereco.isEmpty() || bairro.isEmpty() || telefone.isEmpty() || email.isEmpty()
                || municipio == null || diretor == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Escola escola = new Escola();
        escola.setNome(nome);
        escola.setEnderecoCompleto(endereco + " - " + bairro);
        escola.setTelefone(telefone);
        escola.setEmail(email);
        escola.setMunicipio(municipio);
        escola.setUsuarioDiretor(diretor);

        try {
            escolaController.salvar(escola);
            JOptionPane.showMessageDialog(this, "Escola cadastrada com sucesso.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar escola: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}