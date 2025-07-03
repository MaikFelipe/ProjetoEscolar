/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Usuario;
import model.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaPerfil extends JFrame {

    private Usuario usuario;
    private UsuarioController controller;

    private JTextField tfNome, tfCpf, tfEmail, tfTelefone, tfCargo, tfLogin;
    private JPasswordField pfSenha;
    private JButton btnSalvar, btnVoltar;

    public TelaPerfil(Usuario usuario) {
        this.usuario = usuario;
        this.controller = new UsuarioController();

        configurarJanela();
        criarComponentes();
        preencherCampos();
    }

    private void configurarJanela() {
        setTitle("Meu Perfil");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void criarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lbNome = new JLabel("Nome:");
        tfNome = new JTextField(25);

        JLabel lbCpf = new JLabel("CPF:");
        tfCpf = new JTextField(15);

        JLabel lbEmail = new JLabel("E-mail:");
        tfEmail = new JTextField(25);

        JLabel lbTelefone = new JLabel("Telefone:");
        tfTelefone = new JTextField(15);

        JLabel lbCargo = new JLabel("Cargo:");
        tfCargo = new JTextField(20);

        JLabel lbLogin = new JLabel("Login:");
        tfLogin = new JTextField(15);

        JLabel lbSenha = new JLabel("Senha:");
        pfSenha = new JPasswordField(15);

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; painel.add(lbNome, gbc);
        gbc.gridx = 1; painel.add(tfNome, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbCpf, gbc);
        gbc.gridx = 1; painel.add(tfCpf, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbEmail, gbc);
        gbc.gridx = 1; painel.add(tfEmail, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbTelefone, gbc);
        gbc.gridx = 1; painel.add(tfTelefone, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbCargo, gbc);
        gbc.gridx = 1; painel.add(tfCargo, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbLogin, gbc);
        gbc.gridx = 1; painel.add(tfLogin, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbSenha, gbc);
        gbc.gridx = 1; painel.add(pfSenha, gbc); y++;

        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        add(painel, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarPerfil());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void preencherCampos() {
        tfNome.setText(usuario.getNomeCompleto());
        tfCpf.setText(usuario.getCpf());
        tfEmail.setText(usuario.getEmail());
        tfTelefone.setText(usuario.getTelefone());
        tfCargo.setText(usuario.getCargo());
        tfLogin.setText(usuario.getLogin());
    }

    private void salvarPerfil() {
        String nome = tfNome.getText().trim();
        String cpf = tfCpf.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String cargo = tfCargo.getText().trim();
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword()).trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
            cargo.isEmpty() || login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuario.setNomeCompleto(nome);
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setCargo(cargo);
            usuario.setLogin(login);
            if (!senha.isEmpty()) {
                usuario.setSenha(senha); // texto puro, o DAO irá criptografar
            }

            controller.atualizarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar perfil: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}