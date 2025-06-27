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
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;
import model.Usuario;
import model.controller.UsuarioController;
import model.util.Criptografia;

public class TelaLogin extends JFrame {

    private JTextField tfLogin;
    private JPasswordField pfSenha;
    private JButton btnEntrar, btnSair;
    private UsuarioController usuarioController;

    public TelaLogin() {
        usuarioController = new UsuarioController();
        configurarJanela();
        criarComponentes();
        configurarTeclaEsc();
    }

    private void configurarJanela() {
        setUndecorated(true);
        setTitle("Login - Sistema Escolar");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        
        ImageIcon fundoIcon = new ImageIcon(getClass().getResource("/resources/fundo.jpg"));
        JLabel backgroundLabel = new JLabel(fundoIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);

        
        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setBounds(225, 160, 350, 250);
        painelLogin.setOpaque(false);

        painelLogin.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "Login - Sistema Escolar",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("SansSerif", Font.BOLD, 18),
            Color.WHITE
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lbLogin = new JLabel("Login:");
        lbLogin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbLogin.setForeground(Color.WHITE);

        tfLogin = new JTextField(20);

        JLabel lbSenha = new JLabel("Senha:");
        lbSenha.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbSenha.setForeground(Color.WHITE);

        pfSenha = new JPasswordField(20);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(100, 30));
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEntrar.addActionListener(e -> {
            try {
                fazerLogin();
            } catch (SQLException ex) {
                System.getLogger(TelaLogin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(100, 30));
        btnSair.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSair.addActionListener(e -> System.exit(0)); // Fecha o app

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

        gbc.gridy = 3;
        painelLogin.add(btnSair, gbc);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.add(backgroundLabel, Integer.valueOf(0));
        layeredPane.add(painelLogin, Integer.valueOf(1));

        setContentPane(layeredPane);
    }

    private void configurarTeclaEsc() {
        getRootPane().registerKeyboardAction(
            e -> System.exit(0),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void fazerLogin() throws SQLException {
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha login e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioController.buscarPorLogin(login);
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