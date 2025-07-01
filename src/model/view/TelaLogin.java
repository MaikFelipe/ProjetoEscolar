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
import model.util.Criptografia;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaLogin extends JFrame {

    private JTextField tfLogin;
    private JPasswordField pfSenha;
    private JButton btnEntrar;

    public TelaLogin() {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblLogin = new JLabel("Login:");
        JLabel lblSenha = new JLabel("Senha:");
        tfLogin = new JTextField(20);
        pfSenha = new JPasswordField(20);
        btnEntrar = new JButton("Entrar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(lblLogin, gbc);
        gbc.gridx = 1;
        painel.add(tfLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(lblSenha, gbc);
        gbc.gridx = 1;
        painel.add(pfSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnEntrar, gbc);

        add(painel, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword()).trim();
        String senhaCriptografada = Criptografia.criptografar(senha);

        try {
            UsuarioController controller = new UsuarioController();
            Usuario usuario = controller.autenticar(login, senhaCriptografada);

            if (usuario != null) {
                int nivelId = usuario.getNivelAcesso();
                dispose();
                switch (nivelId) {
                    case 1:
                        new TelaSecretarioEducacao(usuario).setVisible(true);
                        break;
                    case 2:
                        new TelaSuperUsuario(usuario).setVisible(true);
                        break;
                    case 3:
                        new TelaDiretor(usuario).setVisible(true);
                        break;
                    case 4:
                        new TelaSecretarioEscolar(usuario).setVisible(true);
                        break;
                    case 5:
                        new TelaProfessor(usuario).setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Nível de acesso inválido.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha incorretos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao autenticar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}