package model.view;

import model.Aluno;
import model.Usuario;
import model.controller.AlunoController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaCadastroAluno extends JFrame {

    private JTextField tfNomeCompleto, tfCpf, tfEnderecoCompleto, tfTelefone;
    private JTextField tfNomeResponsavel, tfCpfResponsavel, tfEmailResponsavel, tfTelefoneResponsavel;
    private JTextField tfDataNascimento;
    private JButton btnCadastrar, btnVoltar;
    private AlunoController alunoController;
    private Usuario usuarioLogado;

    public TelaCadastroAluno(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        alunoController = new AlunoController();
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("Cadastro de Aluno");
        setSize(600, 550);                   // tamanho confortável
        setLocationRelativeTo(null);         // centraliza na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        // Deixa com borda padrão do SO, barra de título etc
    }

    private void criarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));  // margem ao redor

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        tfNomeCompleto = new JTextField(30);
        tfCpf = new JTextField(15);
        tfEnderecoCompleto = new JTextField(30);
        tfTelefone = new JTextField(15);
        tfNomeResponsavel = new JTextField(30);
        tfCpfResponsavel = new JTextField(15);
        tfEmailResponsavel = new JTextField(30);
        tfTelefoneResponsavel = new JTextField(15);
        tfDataNascimento = new JTextField(10);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1;
        painel.add(tfNomeCompleto, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Data de Nascimento:"), gbc);
        gbc.gridx = 1;
        painel.add(tfDataNascimento, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        painel.add(tfCpf, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Endereço Completo:"), gbc);
        gbc.gridx = 1;
        painel.add(tfEnderecoCompleto, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        painel.add(tfTelefone, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Nome do Responsável:"), gbc);
        gbc.gridx = 1;
        painel.add(tfNomeResponsavel, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("CPF do Responsável:"), gbc);
        gbc.gridx = 1;
        painel.add(tfCpfResponsavel, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("E-mail do Responsável:"), gbc);
        gbc.gridx = 1;
        painel.add(tfEmailResponsavel, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        painel.add(new JLabel("Telefone do Responsável:"), gbc);
        gbc.gridx = 1;
        painel.add(tfTelefoneResponsavel, gbc);
        y++;

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrarAluno());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal(usuarioLogado).setVisible(true);
        });

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        add(new JScrollPane(painel), BorderLayout.CENTER);
    }

    private void cadastrarAluno() {
    String nome = tfNomeCompleto.getText().trim();
    String dataNascStr = tfDataNascimento.getText().trim();
    String cpf = tfCpf.getText().trim();
    String endereco = tfEnderecoCompleto.getText().trim();
    String telefone = tfTelefone.getText().trim();
    String nomeResp = tfNomeResponsavel.getText().trim();
    String cpfResp = tfCpfResponsavel.getText().trim();
    String emailResp = tfEmailResponsavel.getText().trim();
    String telResp = tfTelefoneResponsavel.getText().trim();

    if (nome.isEmpty() || dataNascStr.isEmpty() || cpf.isEmpty() || endereco.isEmpty() || telefone.isEmpty()
            || nomeResp.isEmpty() || cpfResp.isEmpty() || emailResp.isEmpty() || telResp.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    LocalDate dataNascimento;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dataNascimento = LocalDate.parse(dataNascStr, formatter);
    } catch (DateTimeParseException ex) {
        JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd-MM-aaaa.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Aluno aluno = new Aluno();
    aluno.setNomeCompleto(nome);
    aluno.setDataNascimento(dataNascimento);
    aluno.setCpf(cpf);
    aluno.setEnderecoCompleto(endereco);
    aluno.setTelefone(telefone);
    aluno.setNomeResponsavel(nomeResp);
    aluno.setCpfResponsavel(cpfResp);
    aluno.setEmailResponsavel(emailResp);
    aluno.setTelefoneresponsavel(telResp);

    try {
        alunoController.cadastrarAluno(aluno);
        JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
        dispose();
        new TelaPrincipal(usuarioLogado).setVisible(true);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }
}