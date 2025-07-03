/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Turma;
import model.controller.TurmaController;
import model.util.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaTurmas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnVerMais, btnVoltar;
    private TurmaController controller;
    private List<Turma> listaTurmas;
    private Connection conn;

    public TelaTurmas() {
        try {
            conn = Conexao.getConexao();
            controller = new TurmaController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage());
            dispose();
            return;
        }

        setTitle("Gerenciar Turmas");
        setUndecorated(true);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new DefaultTableModel(new String[]{
                "Nome", "Série", "Nível de Ensino", "Ano Letivo", "Turno",
                "Mín. Alunos", "Máx. Alunos"
        }, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelBotoes.setPreferredSize(new Dimension(140, getHeight()));

        Font fonteBotao = new Font("Arial", Font.PLAIN, 11);
        Dimension tamanhoBotao = new Dimension(100, 25);

        btnNovo = criarBotao("NOVO", fonteBotao, tamanhoBotao);
        btnEditar = criarBotao("EDITAR", fonteBotao, tamanhoBotao);
        btnExcluir = criarBotao("EXCLUIR", fonteBotao, tamanhoBotao);
        btnVerMais = criarBotao("VER MAIS", fonteBotao, tamanhoBotao);
        btnVoltar = criarBotao("VOLTAR", fonteBotao, tamanhoBotao);

        painelBotoes.add(Box.createVerticalGlue());
        painelBotoes.add(btnNovo);
        painelBotoes.add(Box.createVerticalStrut(5));
        painelBotoes.add(btnEditar);
        painelBotoes.add(Box.createVerticalStrut(5));
        painelBotoes.add(btnExcluir);
        painelBotoes.add(Box.createVerticalStrut(5));
        painelBotoes.add(btnVerMais);
        painelBotoes.add(Box.createVerticalStrut(5));
        painelBotoes.add(btnVoltar);
        painelBotoes.add(Box.createVerticalGlue());

        painelPrincipal.add(painelBotoes, BorderLayout.EAST);
        add(painelPrincipal, BorderLayout.CENTER);

        carregarTabela();

        btnNovo.addActionListener(e -> new TelaCadastroTurma(this, null).setVisible(true));

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Turma turma = listaTurmas.get(linha);
                new TelaCadastroTurma(this, turma).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                int confirmacao = JOptionPane.showConfirmDialog(this,
                        "Deseja excluir a turma selecionada?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        Turma turma = listaTurmas.get(linha);
                        String resultado = controller.excluirTurma(turma.getId());
                        JOptionPane.showMessageDialog(this, resultado);
                        carregarTabela();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir turma: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para excluir.");
            }
        });

        btnVerMais.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Turma turma = listaTurmas.get(linha);
                String detalhes = String.format(
                        "Nome: %s\nSérie: %s\nNível de Ensino: %s\nAno Letivo: %d\nTurno: %s\nNúmero Mínimo de Alunos: %d\nNúmero Máximo de Alunos: %d\nEscola: %s\nProfessor Responsável: %s",
                        turma.getNome(),
                        turma.getSerie(),
                        turma.getNivelEnsino(),
                        turma.getAnoLetivo(),
                        turma.getTurno(),
                        turma.getNumeroMinimoAlunos(),
                        turma.getNumeroMaximoAlunos(),
                        turma.getEscola() != null ? turma.getEscola().getNome() : "N/A",
                        turma.getProfessorResponsavel() != null ? turma.getProfessorResponsavel().toString() : "N/A"
                );
                JOptionPane.showMessageDialog(this, detalhes, "Detalhes da Turma", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para ver detalhes.");
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
            listaTurmas = controller.listarTurmas();
            if (listaTurmas != null) {
                for (Turma t : listaTurmas) {
                    modeloTabela.addRow(new Object[]{
                            t.getNome(),
                            t.getSerie(),
                            t.getNivelEnsino(),
                            t.getAnoLetivo(),
                            t.getTurno(),
                            t.getNumeroMinimoAlunos(),
                            t.getNumeroMaximoAlunos()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaTurmas().setVisible(true));
    }
}