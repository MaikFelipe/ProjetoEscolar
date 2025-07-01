/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Usuario;
import model.controller.UsuarioController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaUsuarios extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo, btnEditar, btnExcluir, btnVerMais, btnVoltar;
    private UsuarioController controller;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioLogado;

    public TelaUsuarios(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        controller = new UsuarioController();

        setTitle("Gerenciar Usuários");
        setUndecorated(true);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new DefaultTableModel(new String[]{
            "ID", "Nome", "Login", "Nível de Acesso"
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
            TelaCadastroUsuario cadastro = new TelaCadastroUsuario(usuarioLogado);
            cadastro.setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Usuario usuario = listaUsuarios.get(linhaSelecionada);
                TelaCadastroUsuario cadastro = new TelaCadastroUsuario(usuarioLogado, usuario);
                cadastro.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir o usuário selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    Usuario usuario = listaUsuarios.get(linhaSelecionada);
                    try {
                        controller.excluirUsuario(usuario.getId());
                        carregarTabela();
                        JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir usuário: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
            }
        });

        btnVerMais.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                Usuario usuario = listaUsuarios.get(linhaSelecionada);
                String detalhes = String.format(
                    "Nome: %s\nLogin: %s\nNível de Acesso: %d",
                    usuario.getNomeCompleto(),
                    usuario.getLogin(),
                    usuario.getNivelAcesso()
                );
                JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Usuário", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para ver detalhes.");
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

            int nivelVisualizar = usuarioLogado.getNivelAcesso() + 1;

            if (nivelVisualizar <= 5) {
                listaUsuarios = controller.listarPorNivelAcesso(nivelVisualizar);
            } else {
                listaUsuarios = new ArrayList<>();
            }

            for (Usuario u : listaUsuarios) {
                modeloTabela.addRow(new Object[]{
                    u.getId(),
                    u.getNomeCompleto(),
                    u.getLogin(),
                    u.getNivelAcesso()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario admin = new Usuario();
            admin.setId(1);
            admin.setNomeCompleto("Administrador");
            admin.setLogin("admin");
            admin.setNivelAcesso(1); // Nível de acesso 1 vê nível 2
            new TelaUsuarios(admin).setVisible(true);
        });
    }
}