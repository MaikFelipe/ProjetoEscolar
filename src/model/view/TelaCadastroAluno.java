/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Aluno;
import model.controller.AlunoController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;

public class TelaCadastroAluno extends JDialog {

    private JTextField tfNome, tfDataNascimento, tfCpf, tfEndereco, tfTelefone;
    private JTextField tfNomeResponsavel, tfCpfResponsavel, tfEmailResponsavel, tfTelefoneResponsavel;
    private JButton btnSalvar, btnCancelar;
    private AlunoController controller;
    private Aluno aluno;
    private TelaAlunos telaAlunos;

    public TelaCadastroAluno(JFrame parent, Aluno aluno) {
        super(parent, "Cadastro de Aluno", true);
        this.aluno = aluno;
        this.telaAlunos = (TelaAlunos) parent;

        controller = new AlunoController();

        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(10, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfNome = new JTextField();
        tfDataNascimento = new JTextField();
        tfCpf = new JTextField();
        tfEndereco = new JTextField();
        tfTelefone = new JTextField();
        tfNomeResponsavel = new JTextField();
        tfCpfResponsavel = new JTextField();
        tfEmailResponsavel = new JTextField();
        tfTelefoneResponsavel = new JTextField();

        painel.add(new JLabel("Nome Completo:"));
        painel.add(tfNome);
        painel.add(new JLabel("Data de Nascimento (dd-MM-yyyy):"));
        painel.add(tfDataNascimento);
        painel.add(new JLabel("CPF:"));
        painel.add(tfCpf);
        painel.add(new JLabel("Endereço:"));
        painel.add(tfEndereco);
        painel.add(new JLabel("Telefone:"));
        painel.add(tfTelefone);
        painel.add(new JLabel("Nome do Responsável:"));
        painel.add(tfNomeResponsavel);
        painel.add(new JLabel("CPF do Responsável:"));
        painel.add(tfCpfResponsavel);
        painel.add(new JLabel("Email do Responsável:"));
        painel.add(tfEmailResponsavel);
        painel.add(new JLabel("Telefone do Responsável:"));
        painel.add(tfTelefoneResponsavel);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        if (aluno != null) {
            preencherCampos();
        }

        btnSalvar.addActionListener(e -> salvarAluno());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void preencherCampos() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tfNome.setText(aluno.getNomeCompleto());
        tfDataNascimento.setText(aluno.getDataNascimento().format(formatter));
        tfCpf.setText(aluno.getCpf());
        tfEndereco.setText(aluno.getEnderecoCompleto());
        tfTelefone.setText(aluno.getTelefone());
        tfNomeResponsavel.setText(aluno.getNomeResponsavel());
        tfCpfResponsavel.setText(aluno.getCpfResponsavel());
        tfEmailResponsavel.setText(aluno.getEmailResponsavel());
        tfTelefoneResponsavel.setText(aluno.getTelefoneResponsavel());
    }

    private void salvarAluno() {
        try {
            if (aluno == null) {
                aluno = new Aluno();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            aluno.setNomeCompleto(tfNome.getText());
            aluno.setDataNascimento(LocalDate.parse(tfDataNascimento.getText(), formatter));
            aluno.setCpf(tfCpf.getText());
            aluno.setEnderecoCompleto(tfEndereco.getText());
            aluno.setTelefone(tfTelefone.getText());
            aluno.setNomeResponsavel(tfNomeResponsavel.getText());
            aluno.setCpfResponsavel(tfCpfResponsavel.getText());
            aluno.setEmailResponsavel(tfEmailResponsavel.getText());
            aluno.setTelefoneResponsavel(tfTelefoneResponsavel.getText());

            controller.salvarAluno(aluno);
            telaAlunos.carregarTabela();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: " + e.getMessage());
        }
    }
}