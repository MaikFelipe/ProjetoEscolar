/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Escola;
import model.controller.EscolaController;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaEscolas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnVerMais, btnVoltar;
    private EscolaController controller;
    private List<Escola> listaEscolas;

    public TelaEscolas() {
        setTitle("Gerenciar Escolas");
        setUndecorated(true);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            Connection conn = Conexao.getConexao();
            controller = new EscolaController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro na conexão: " + e.getMessage());
            return;
        }

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new DefaultTableModel(new String[]{
            "Nome", "Município", "Bairro", "Telefone", "Diretor"
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

        btnNovo.addActionListener(e -> new TelaCadastroEscola(this, null).setVisible(true));

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Escola escola = listaEscolas.get(linha);
                new TelaCadastroEscola(this, escola).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma escola para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir a escola selecionada?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        Escola escola = listaEscolas.get(linha);
                        controller.excluir(escola.getId());
                        carregarTabela();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir escola: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma escola para excluir.");
            }
        });

        btnVerMais.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                Escola escola = listaEscolas.get(linha);
                String detalhes = String.format(
                    "Nome: %s\nEndereço: %s\nBairro: %s\nMunicípio ID: %d\nTelefone: %s\nEmail: %s\nDiretor ID: %d",
                    escola.getNome(),
                    escola.getEnderecoCompleto(),
                    escola.getBairro(),
                    escola.getMunicipio().getId(),
                    escola.getTelefone(),
                    escola.getEmail(),
                    escola.getUsuarioDiretor().getId()
                );
                JOptionPane.showMessageDialog(this, detalhes, "Detalhes da Escola", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma escola para ver detalhes.");
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
            listaEscolas = controller.listarTodos();
            for (Escola e : listaEscolas) {
                modeloTabela.addRow(new Object[]{
                    e.getNome(),
                    e.getMunicipio().getId(),
                    e.getBairro(),
                    e.getTelefone(),
                    e.getUsuarioDiretor().getId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar escolas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaEscolas().setVisible(true));
    }
}