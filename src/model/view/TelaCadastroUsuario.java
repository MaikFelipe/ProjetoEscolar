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

        cbNivelAcesso = new JComboBox<>(niveisPermitidos(usuarioLogado.getNivelAcesso()));

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

        String nivelStr = nivelAcessoIdParaString(usuarioEditando.getNivelAcesso());

        boolean existe = false;
        for (int i = 0; i < cbNivelAcesso.getItemCount(); i++) {
            if (cbNivelAcesso.getItemAt(i).equals(nivelStr)) {
                existe = true;
                break;
            }
        }
        if (existe) {
            cbNivelAcesso.setSelectedItem(nivelStr);
        }
    }

    private void salvarUsuario() {
        String nome = tfNome.getText().trim();
        String cpf = tfCpf.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String cargo = tfCargo.getText().trim();
        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword()).trim();
        String nivelAcessoStr = (String) cbNivelAcesso.getSelectedItem();
        int nivelAcessoId = nivelAcessoStringParaId(nivelAcessoStr);

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
            cargo.isEmpty() || login.isEmpty() || (usuarioEditando == null && senha.isEmpty())) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!podeCadastrarNivel(usuarioLogado.getNivelAcesso(), nivelAcessoId)) {
            JOptionPane.showMessageDialog(this, "Você não tem permissão para cadastrar usuários desse nível.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (usuarioEditando == null) {
                Usuario novo = new Usuario();
                novo.setNomeCompleto(nome);
                novo.setCpf(cpf);
                novo.setEmail(email);
                novo.setTelefone(telefone);
                novo.setCargo(cargo);
                novo.setLogin(login);
                novo.setSenha(senha); // senha em texto puro
                novo.setNivelAcesso(nivelAcessoId);

                usuarioController.cadastrarUsuario(novo);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            } else {
                usuarioEditando.setNomeCompleto(nome);
                usuarioEditando.setCpf(cpf);
                usuarioEditando.setEmail(email);
                usuarioEditando.setTelefone(telefone);
                usuarioEditando.setCargo(cargo);
                usuarioEditando.setLogin(login);
                if (!senha.isEmpty()) {
                    usuarioEditando.setSenha(senha); // senha em texto puro
                }
                usuarioEditando.setNivelAcesso(nivelAcessoId);

                usuarioController.atualizarUsuario(usuarioEditando);
                JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            }
            dispose();
        } catch (SQLException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean podeCadastrarNivel(int nivelUsuarioLogado, int nivelSelecionado) {
        switch (nivelUsuarioLogado) {
            case 1: // Secretário Educação
                return nivelSelecionado == 2 || nivelSelecionado == 3;
            case 2: // SuperUsuario
                return nivelSelecionado == 3;
            case 3: // Diretor
                return nivelSelecionado == 4;
            case 4: // Secretário Escolar
                return nivelSelecionado == 5;
            default:
                return false;
        }
    }

    private String[] niveisPermitidos(int nivelUsuarioLogado) {
        switch (nivelUsuarioLogado) {
            case 1:
                return new String[] { "SuperUsuario", "Diretor" };
            case 2:
                return new String[] { "Diretor" };
            case 3:
                return new String[] { "SecretarioEscolar" };
            case 4:
                return new String[] { "Professor" };
            default:
                return new String[] {};
        }
    }

    private int nivelAcessoStringParaId(String nivel) {
        switch (nivel) {
            case "SecretarioEducacao": return 1;
            case "SuperUsuario": return 2;
            case "Diretor": return 3;
            case "SecretarioEscolar": return 4;
            case "Professor": return 5;
            default: return 0;
        }
    }

    private String nivelAcessoIdParaString(int id) {
        switch (id) {
            case 1: return "SecretarioEducacao";
            case 2: return "SuperUsuario";
            case 3: return "Diretor";
            case 4: return "SecretarioEscolar";
            case 5: return "Professor";
            default: return "";
        }
    }
}