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
import javax.swing.*;
import java.awt.*;

public class TelaProfessor extends JFrame {

    private Usuario usuarioLogado;
    private final Color fundoClaro = new Color(245, 245, 245);

    public TelaProfessor(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        if (usuarioLogado.getNivelAcesso() != 5) {
            JOptionPane.showMessageDialog(null, "Acesso negado. Você não tem permissão para acessar esta tela.");
            dispose();
            return;
        }

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(fundoClaro);
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        painelSuperior.setBackground(fundoClaro);

        JLabel mensagem = new JLabel("Bem-vindo, Professor " + usuarioLogado.getNomeCompleto(), SwingConstants.CENTER);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 22));
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelSuperior.add(mensagem);
        add(painelSuperior, BorderLayout.NORTH);

        JLabel titulo = new JLabel("Sistema de Gestão Escolar - Professor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.SOUTH);

        JPanel painelCentral = new JPanel(new GridLayout(0, 2, 30, 30));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));
        painelCentral.setBackground(fundoClaro);

        painelCentral.add(criarCard("Lançar Notas", e -> abrirTelaLancamentoNotas()));
        painelCentral.add(criarCard("Gerenciar Notas", e -> abrirTelaGerenciarNotas()));
        painelCentral.add(criarCard("Registrar Frequência", e -> abrirTelaRegistrarFrequencia()));
        painelCentral.add(criarCard("Gerenciar Frequência", e -> abrirTelaGerenciarFrequencia()));
        painelCentral.add(criarCard("Sair", e -> sair()));

        add(painelCentral, BorderLayout.CENTER);
    }

    private JPanel criarCard(String texto, java.awt.event.ActionListener acao) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setPreferredSize(new Dimension(400, 50));
        botao.setMaximumSize(new Dimension(400, 50));
        botao.setMinimumSize(new Dimension(400, 50));
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(true);
        botao.addActionListener(acao);

        card.add(botao, BorderLayout.CENTER);
        return card;
    }

    private void abrirTelaLancamentoNotas() {
        new TelaLancarNotas().setVisible(true);
    }

    private void abrirTelaGerenciarNotas() {
        new TelaGerenciarNotas(usuarioLogado).setVisible(true);
    }

    private void abrirTelaRegistrarFrequencia() {
        new TelaRegistrarFrequencia(usuarioLogado).setVisible(true);
    }

    private void abrirTelaGerenciarFrequencia() {
        new TelaGerenciarFrequencia(usuarioLogado).setVisible(true);
    }

    private void sair() {
        dispose();
        new TelaLogin().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario();
            u.setId(5);
            u.setNomeCompleto("João Professor");
            u.setLogin("joao.prof");
            u.setNivelAcesso(5);
            new TelaProfessor(u).setVisible(true);
        });
    }
}
