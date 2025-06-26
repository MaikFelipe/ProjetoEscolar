package model.view;

import javax.swing.*;
import java.awt.*;
import model.Usuario;
import model.controller.UsuarioController;
import model.util.Criptografia;

public class TelaCadastroUsuario extends JFrame {

    private JTextField tfNome, tfCpf, tfEmail, tfLogin, tfTelefone, tfCargo;
    private JPasswordField pfSenha;
    private JComboBox<String> cbNivelAcesso;
    private JButton btnCadastrar, btnVoltar;
    private UsuarioController usuarioController;
    private Usuario usuarioLogado;

    public TelaCadastroUsuario(Usuario usuario) {
        this.usuarioLogado = usuario;
        usuarioController = new UsuarioController();
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("Cadastro de Usuário");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel lbNivelAcesso = new JLabel("Nível de Acesso:");
        cbNivelAcesso = new JComboBox<>(new String[] {
            "SecretarioEducacao", "SuperUsuario", "Diretor", "SecretarioEscolar", "Professor"
        });

        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal(usuarioLogado).setVisible(true);
        });

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

        gbc.gridx = 0; gbc.gridy = y; painel.add(lbNivelAcesso, gbc);
        gbc.gridx = 1; painel.add(cbNivelAcesso, gbc); y++;

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        add(painel, BorderLayout.CENTER);
    }

    private void cadastrarUsuario() {
        String nome = tfNome.getText().trim();
        String cpf = tfCpf.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String cargo = tfCargo.getText().trim();
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword()).trim();
        String nivelAcesso = (String) cbNivelAcesso.getSelectedItem();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
            cargo.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String senhaCriptografada = Criptografia.criptografar(senha);

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        usuario.setCargo(cargo);
        usuario.setLogin(login);
        usuario.setSenha(senhaCriptografada);
        usuario.setNivelAcesso(nivelAcesso);

        String resultado = usuarioController.cadastrarUsuario(usuario);
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.contains("sucesso")) {
            dispose();
            new TelaPrincipal(usuarioLogado).setVisible(true);
        }
    }
}