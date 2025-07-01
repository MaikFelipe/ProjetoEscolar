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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.SQLException;

public class TelaAlunos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnVerMais, btnVoltar;
    private AlunoController controller;
    private List<Aluno> listaAlunos;

    public TelaAlunos() {
        setTitle("Gerenciar Alunos");
        setUndecorated(true);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        controller = new AlunoController();

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new DefaultTableModel(new String[]{
            "Nome", "Data Nasc.", "CPF", "Endereço", "Telefone"
        }, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelBotoes.setPreferredSize(new Dimension(140, getHeight()));

        JPanel painelInterno = new JPanel();
        painelInterno.setLayout(new BoxLayout(painelInterno, BoxLayout.Y_AXIS));
        painelInterno.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font fonteBotao = new Font("Arial", Font.PLAIN, 11);
        Dimension tamanhoBotao = new Dimension(100, 25);

        btnNovo = criarBotao("NOVO", fonteBotao, tamanhoBotao);
        btnEditar = criarBotao("EDITAR", fonteBotao, tamanhoBotao);
        btnExcluir = criarBotao("EXCLUIR", fonteBotao, tamanhoBotao);
        btnVerMais = criarBotao("VER MAIS", fonteBotao, tamanhoBotao);
        btnVoltar = criarBotao("VOLTAR", fonteBotao, tamanhoBotao);

        painelInterno.add(btnNovo);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnEditar);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnExcluir);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnVerMais);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnVoltar);

        painelBotoes.add(Box.createVerticalGlue());
        painelBotoes.add(painelInterno);
        painelBotoes.add(Box.createVerticalGlue());

        painelPrincipal.add(painelBotoes, BorderLayout.EAST);
        add(painelPrincipal, BorderLayout.CENTER);

        carregarTabela();

        btnNovo.addActionListener(e -> {
            TelaCadastroAluno cadastro = new TelaCadastroAluno(this, null);
            cadastro.setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Aluno aluno = listaAlunos.get(linhaSelecionada);
                TelaCadastroAluno cadastro = new TelaCadastroAluno(this, aluno);
                cadastro.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir o aluno selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    Aluno aluno = listaAlunos.get(linhaSelecionada);
                    try {
                        controller.deletarAluno(aluno.getId());
                        carregarTabela();
                    } catch (SQLException ex) {
                        if (ex.getMessage().contains("foreign key constraint fails")) {
                            JOptionPane.showMessageDialog(this, "Não é possível excluir o aluno pois existem dados relacionados a ele, como frequência ou matrícula.", "Erro de Exclusão", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + ex.getMessage());
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir.");
            }
        });

        btnVerMais.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Aluno aluno = listaAlunos.get(linhaSelecionada);
                String detalhes = String.format(
                    "Nome: %s\nData de Nascimento: %s\nCPF: %s\nEndereço: %s\nTelefone: %s\n\n" +
                    "Responsável \nNome: %s\nCPF: %s\nEmail: %s\nTelefone: %s",
                    aluno.getNomeCompleto(),
                    aluno.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    aluno.getCpf(),
                    aluno.getEnderecoCompleto(),
                    aluno.getTelefone(),
                    aluno.getNomeResponsavel(),
                    aluno.getCpfResponsavel(),
                    aluno.getEmailResponsavel(),
                    aluno.getTelefoneResponsavel()
                );
                JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Aluno", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para ver detalhes.");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private JButton criarBotao(String texto, Font fonte, Dimension tamanho) {
        JButton botao = new JButton(texto);
        botao.setFont(fonte);
        botao.setMaximumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    public void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);
            listaAlunos = controller.listarAlunos();
            for (Aluno a : listaAlunos) {
                modeloTabela.addRow(new Object[]{
                    a.getNomeCompleto(),
                    a.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    a.getCpf(),
                    a.getEnderecoCompleto(),
                    a.getTelefone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaAlunos().setVisible(true);
        });
    }
}