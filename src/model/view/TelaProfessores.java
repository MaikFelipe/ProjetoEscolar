/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Professor;
import model.Usuario;
import model.controller.ProfessorController;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaProfessores extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnVerMais, btnVerPerfil, btnVoltar;
    private ProfessorController professorController;
    private List<Professor> listaProfessores;
    private Usuario usuarioLogado;

    public TelaProfessores(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        try {
            professorController = new ProfessorController();  // construtor sem parâmetros
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao inicializar controller: " + e.getMessage());
            dispose();
            return;
        }

        setTitle("Gerenciar Professores");
        setUndecorated(true);
        setSize(750, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Colunas importantes na tabela
        modeloTabela = new DefaultTableModel(new String[]{
                "ID", "Nome Completo", "Telefone", "Observações"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelBotoes.setPreferredSize(new Dimension(160, getHeight()));

        JPanel painelInterno = new JPanel();
        painelInterno.setLayout(new BoxLayout(painelInterno, BoxLayout.Y_AXIS));
        painelInterno.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font fonteBotao = new Font("Arial", Font.PLAIN, 11);
        Dimension tamanhoBotao = new Dimension(120, 25);

        btnNovo = criarBotao("NOVO", fonteBotao, tamanhoBotao);
        btnEditar = criarBotao("EDITAR", fonteBotao, tamanhoBotao);
        btnExcluir = criarBotao("EXCLUIR", fonteBotao, tamanhoBotao);
        btnVerMais = criarBotao("VER MAIS", fonteBotao, tamanhoBotao);
        btnVerPerfil = criarBotao("VER PERFIL", fonteBotao, tamanhoBotao);
        btnVoltar = criarBotao("VOLTAR", fonteBotao, tamanhoBotao);

        painelInterno.add(btnNovo);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnEditar);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnExcluir);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnVerMais);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnVerPerfil);
        painelInterno.add(Box.createVerticalStrut(5));
        painelInterno.add(btnVoltar);

        painelBotoes.add(Box.createVerticalGlue());
        painelBotoes.add(painelInterno);
        painelBotoes.add(Box.createVerticalGlue());

        painelPrincipal.add(painelBotoes, BorderLayout.EAST);
        add(painelPrincipal, BorderLayout.CENTER);

        aplicarPermissoes();

        carregarTabela();

        btnNovo.addActionListener(e -> {
            TelaCadastroProfessor cadastro = new TelaCadastroProfessor(this, null);
            cadastro.setVisible(true);
            cadastro.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    carregarTabela();
                }
            });
        });

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Professor professor = listaProfessores.get(linhaSelecionada);
                TelaCadastroProfessor cadastro = new TelaCadastroProfessor(this, professor);
                cadastro.setVisible(true);
                cadastro.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        carregarTabela();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                int confirmacao = JOptionPane.showConfirmDialog(this,
                        "Deseja excluir o professor selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    Professor professor = listaProfessores.get(linhaSelecionada);
                    try {
                        professorController.excluirProfessor(professor.getId());
                        carregarTabela();
                        JOptionPane.showMessageDialog(this, "Professor excluído com sucesso.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir professor: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para excluir.");
            }
        });

        btnVerMais.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Professor professor = listaProfessores.get(linhaSelecionada);
                Usuario u = professor.getUsuario();
                String detalhes = String.format(
                        "ID: %d\nNome Completo: %s\nCPF: %s\nEmail: %s\nTelefone: %s\nCargo: %s\nLogin: %s\n" +
                        "Observações: %s\nDisciplina: %s",
                        professor.getId(),
                        u.getNomeCompleto(),
                        u.getCpf(),
                        u.getEmail(),
                        u.getTelefone(),
                        u.getCargo(),
                        u.getLogin(),
                        professor.getObservacoes() != null ? professor.getObservacoes() : "-",
                        professor.getDisciplina() != null ? professor.getDisciplina().getNome() : "-"
                );
                JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Professor", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para ver detalhes.");
            }
        });

        btnVerPerfil.addActionListener(e -> {
            new TelaPerfil(usuarioLogado).setVisible(true);
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void aplicarPermissoes() {
        int nivel = usuarioLogado.getNivelAcesso();

        if (nivel == 5) {
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    private JButton criarBotao(String texto, Font fonte, Dimension tamanho) {
        JButton botao = new JButton(texto);
        botao.setFont(fonte);
        botao.setMaximumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);
            listaProfessores = professorController.listarProfessores();

            if (listaProfessores == null) {
                listaProfessores = new ArrayList<>();
            }

            for (Professor p : listaProfessores) {
                Usuario u = p.getUsuario();
                modeloTabela.addRow(new Object[]{
                        p.getId(),
                        u.getNomeCompleto(),
                        u.getTelefone(),
                        p.getObservacoes() != null ? p.getObservacoes() : ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar professores: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario admin = new Usuario();
            admin.setId(1);
            admin.setNomeCompleto("Administrador");
            admin.setLogin("admin");
            admin.setNivelAcesso(1);
            new TelaProfessores(admin).setVisible(true);
        });
    }
}