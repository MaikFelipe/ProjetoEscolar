package model.view;

import javax.swing.*;
import java.awt.*;
import model.Usuario;
import model.controller.UsuarioController;
import model.util.Criptografia;
import java.sql.SQLException;

public class TelaCadastroUsuario extends JFrame {

    private JTextField tfNome, tfCpf, tfEmail, tfLogin, tfTelefone, tfCargo;
    private JPasswordField pfSenha;
    private JComboBox<String> cbNivelAcesso;
    private JButton btnCadastrar, btnVoltar;
    private UsuarioController usuarioController;
    private Usuario usuarioLogado;
    private Usuario usuarioEditando;

    public TelaCadastroUsuario(Usuario usuarioLogado) {
        this(usuarioLogado, null);
    }

    public TelaCadastroUsuario(Usuario usuarioLogado, Usuario usuarioEditando) {
        this.usuarioLogado = usuarioLogado;
        this.usuarioEditando = usuarioEditando;
        usuarioController = new UsuarioController();
        configurarJanela();
        criarComponentes();
        if (usuarioEditando != null) preencherCampos();
    }

    private void configurarJanela() {
        setTitle(usuarioEditando == null ? "Cadastro de Usuário" : "Editar Usuário");
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

        btnCadastrar = new JButton(usuarioEditando == null ? "Cadastrar" : "Salvar");
        btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> salvarUsuario());
        btnVoltar.addActionListener(e -> dispose());

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

    private void preencherCampos() {
        tfNome.setText(usuarioEditando.getNomeCompleto());
        tfCpf.setText(usuarioEditando.getCpf());
        tfEmail.setText(usuarioEditando.getEmail());
        tfTelefone.setText(usuarioEditando.getTelefone());
        tfCargo.setText(usuarioEditando.getCargo());
        tfLogin.setText(usuarioEditando.getLogin());
        cbNivelAcesso.setSelectedItem(usuarioEditando.getNivelAcesso());
    }

    private void salvarUsuario() {
        String nome = tfNome.getText().trim();
        String cpf = tfCpf.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String cargo = tfCargo.getText().trim();
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword()).trim();
        String nivelAcesso = (String) cbNivelAcesso.getSelectedItem();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
            cargo.isEmpty() || login.isEmpty() || (usuarioEditando == null && senha.isEmpty())) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioEditando == null) {
            String senhaCriptografada = Criptografia.criptografar(senha);
            Usuario novo = new Usuario();
            novo.setNomeCompleto(nome);
            novo.setCpf(cpf);
            novo.setEmail(email);
            novo.setTelefone(telefone);
            novo.setCargo(cargo);
            novo.setLogin(login);
            novo.setSenha(senhaCriptografada);
            novo.setNivelAcesso(nivelAcesso);

            try {
                usuarioController.cadastrarUsuario(novo);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                dispose();
            } catch (SQLException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            usuarioEditando.setNomeCompleto(nome);
            usuarioEditando.setCpf(cpf);
            usuarioEditando.setEmail(email);
            usuarioEditando.setTelefone(telefone);
            usuarioEditando.setCargo(cargo);
            usuarioEditando.setLogin(login);
            if (!senha.isEmpty()) {
                usuarioEditando.setSenha(Criptografia.criptografar(senha));
            }
            usuarioEditando.setNivelAcesso(nivelAcesso);

            try {
                usuarioController.atualizarUsuario(usuarioEditando);
                JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
                dispose();
            } catch (SQLException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}