/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Escola;
import model.Municipio;
import model.Usuario;
import model.controller.EscolaController;
import model.controller.MunicipioController;
import model.controller.UsuarioController;
import model.util.Conexao;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaCadastroEscola extends JFrame {

    private JTextField tfNome, tfEndereco, tfBairro, tfTelefone, tfEmail;
    private JComboBox<Municipio> cbMunicipio;
    private JComboBox<Usuario> cbDiretor;
    private JButton btnSalvar, btnCancelar;
    private EscolaController escolaController;
    private MunicipioController municipioController;
    private UsuarioController usuarioController;
    private Escola escola;
    private TelaEscolas telaEscolas;

    public TelaCadastroEscola(TelaEscolas telaEscolas, Escola escola) {
        this.telaEscolas = telaEscolas;
        this.escola = escola;

        setTitle(escola == null ? "Nova Escola" : "Editar Escola");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        try {
            Connection conn = Conexao.getConexao();
            escolaController = new EscolaController(conn);
            municipioController = new MunicipioController(conn);
            usuarioController = new UsuarioController();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage());
            return;
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfNome = new JTextField();
        tfEndereco = new JTextField();
        tfBairro = new JTextField();
        tfTelefone = new JTextField();
        tfEmail = new JTextField();
        cbMunicipio = new JComboBox<>();
        cbDiretor = new JComboBox<>();

        carregarMunicipios();
        carregarDiretores();

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(tfNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Endereço Completo:"), gbc);
        gbc.gridx = 1;
        add(tfEndereco, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Bairro:"), gbc);
        gbc.gridx = 1;
        add(tfBairro, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Município:"), gbc);
        gbc.gridx = 1;
        add(cbMunicipio, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        add(tfTelefone, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(tfEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Diretor:"), gbc);
        gbc.gridx = 1;
        add(cbDiretor, gbc);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0; gbc.gridy++;
        add(btnSalvar, gbc);
        gbc.gridx = 1;
        add(btnCancelar, gbc);

        if (escola != null) {
            preencherCampos();
        }

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarMunicipios() {
        try {
            List<Municipio> municipios = municipioController.listarTodos();
            for (Municipio m : municipios) {
                cbMunicipio.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar municípios: " + e.getMessage());
        }
    }

    private void carregarDiretores() {
        try {
            List<Usuario> diretores = usuarioController.listarPorNivelAcesso(3);
            cbDiretor.removeAllItems();
            for (Usuario u : diretores) {
                cbDiretor.addItem(u);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar diretores: " + e.getMessage());
        }
    }


    private void preencherCampos() {
        tfNome.setText(escola.getNome());
        tfEndereco.setText(escola.getEnderecoCompleto());
        tfBairro.setText(escola.getBairro());
        tfTelefone.setText(escola.getTelefone());
        tfEmail.setText(escola.getEmail());
        cbMunicipio.setSelectedItem(escola.getMunicipio());
        cbDiretor.setSelectedItem(escola.getUsuarioDiretor());
    }

    private void salvar() {
        if (tfNome.getText().isEmpty() || tfEndereco.getText().isEmpty() || tfBairro.getText().isEmpty() ||
                tfTelefone.getText().isEmpty() || tfEmail.getText().isEmpty() ||
                cbMunicipio.getSelectedItem() == null || cbDiretor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        if (escola == null) escola = new Escola();

        escola.setNome(tfNome.getText().trim());
        escola.setEnderecoCompleto(tfEndereco.getText().trim());
        escola.setBairro(tfBairro.getText().trim());
        escola.setTelefone(tfTelefone.getText().trim());
        escola.setEmail(tfEmail.getText().trim());
        escola.setMunicipio((Municipio) cbMunicipio.getSelectedItem());
        escola.setUsuarioDiretor((Usuario) cbDiretor.getSelectedItem());

        try {
            escolaController.salvar(escola);
            telaEscolas.carregarTabela();
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar escola: " + e.getMessage());
        }
    }
} 