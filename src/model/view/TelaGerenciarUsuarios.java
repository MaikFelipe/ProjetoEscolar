/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

import model.Usuario;
import model.controller.UsuarioController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaGerenciarUsuarios extends JFrame {

    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnAtualizar;
    private UsuarioController usuarioController;
    private Usuario usuarioLogado;

    public TelaGerenciarUsuarios(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        usuarioController = new UsuarioController();

        setTitle("Gerenciar Usuários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
        carregarUsuarios();
    }

    private void criarComponentes() {
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Login", "Nível de Acesso"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaUsuarios = new JTable(modeloTabela);
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabelaUsuarios);

        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar");

        btnNovo.addActionListener(e -> abrirCadastroNovo());
        btnEditar.addActionListener(e -> abrirCadastroEditar());
        btnExcluir.addActionListener(e -> excluirUsuario());
        btnAtualizar.addActionListener(e -> carregarUsuarios());

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarUsuarios() {
        modeloTabela.setRowCount(0);
        try {
            List<Usuario> usuarios = usuarioController.listarTodos();
            for (Usuario u : usuarios) {
                modeloTabela.addRow(new Object[]{
                    u.getId(),
                    u.getNomeCompleto(),
                    u.getLogin(),
                    u.getNivelAcesso()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Usuario getUsuarioSelecionado() {
        int linha = tabelaUsuarios.getSelectedRow();
        if (linha == -1) {
            return null;
        }
        int id = (int) modeloTabela.getValueAt(linha, 0);
        String nome = (String) modeloTabela.getValueAt(linha, 1);
        String login = (String) modeloTabela.getValueAt(linha, 2);
        String nivel = (String) modeloTabela.getValueAt(linha, 3);

        Usuario u = new Usuario();
        u.setId(id);
        u.setNomeCompleto(nome);
        u.setLogin(login);
        u.setNivelAcesso(nivel);
        return u;
    }

    private void abrirCadastroNovo() {
        TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(usuarioLogado);
        telaCadastro.setVisible(true);
        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarUsuarios();
            }
        });
    }

    private void abrirCadastroEditar() {
        Usuario selecionado = getUsuarioSelecionado();
        if (selecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            return;
        }

        TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(usuarioLogado, selecionado);
        telaCadastro.setVisible(true);
        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarUsuarios();
            }
        });
    }

    private void excluirUsuario() {
        Usuario selecionado = getUsuarioSelecionado();
        if (selecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirma a exclusão do usuário " + selecionado.getNomeCompleto() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                usuarioController.excluirUsuario(selecionado.getId());
                carregarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir usuário: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}