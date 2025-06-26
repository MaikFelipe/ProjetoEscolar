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
import model.Usuario;
import model.controller.UsuarioController;
import model.util.Criptografia;

public class TelaLogin extends JFrame {

    private JTextField tfLogin;
    private JPasswordField pfSenha;
    private JButton btnEntrar;
    private UsuarioController usuarioController;

    public TelaLogin() {
        usuarioController = new UsuarioController();
        criarComponentes();
        configurarJanela();
    }

    private void criarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setPreferredSize(new Dimension(400, 180));
        painelLogin.setBorder(BorderFactory.createTitledBorder("Login - Sistema Escolar"));

        JLabel lbLogin = new JLabel("Login:");
        tfLogin = new JTextField(20);

        JLabel lbSenha = new JLabel("Senha:");
        pfSenha = new JPasswordField(20);

        btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> fazerLogin());

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelLogin.add(lbLogin, gbc);

        gbc.gridx = 1;
        painelLogin.add(tfLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelLogin.add(lbSenha, gbc);

        gbc.gridx = 1;
        painelLogin.add(pfSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelLogin.add(btnEntrar, gbc);

        gbc = new GridBagConstraints();
        add(painelLogin, gbc);
    }

    private void configurarJanela() {
        setTitle("Login - Sistema Escolar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);
    }

    private void fazerLogin() {
        String login = tfLogin.getText().trim();
        char[] senhaChars = pfSenha.getPassword();
        String senha = new String(senhaChars);

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha login e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioController.buscarUsuarioPorLogin(login);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String senhaCriptografada = Criptografia.criptografar(senha);

        if (senhaCriptografada.equals(usuario.getSenha())) {
            JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            abrirTelaPrincipal(usuario);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPrincipal(Usuario usuario) {
        TelaPrincipal telaPrincipal = new TelaPrincipal(usuario);
        telaPrincipal.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}