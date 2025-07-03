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

public class TelaDiretor extends JFrame {

    private Usuario usuarioLogado;
    private final Color fundoClaro = new Color(245, 245, 245);

    public TelaDiretor(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(fundoClaro);
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        painelSuperior.setBackground(fundoClaro);

        JLabel mensagem = new JLabel("Bem-vindo, Diretor " + usuarioLogado.getNomeCompleto(), SwingConstants.CENTER);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 22));
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelSuperior.add(mensagem);
        add(painelSuperior, BorderLayout.NORTH);

        JLabel titulo = new JLabel("Sistema de Gestão Escolar - Diretor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.SOUTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 400, 50, 400));
        painelCentral.setBackground(fundoClaro);

        painelCentral.add(criarCard("Secretários Escolares.", e -> abrirTelaUsuarios()));
        painelCentral.add(Box.createVerticalStrut(30));
        painelCentral.add(criarCard("Relatórios de Carga Horária", e -> abrirTelaRelatorioCargaHoraria()));
        painelCentral.add(Box.createVerticalStrut(30));
        painelCentral.add(criarCard("Registrar Carga Complementar", e -> abrirTelaRegistrarCargaComplementar()));
        painelCentral.add(Box.createVerticalStrut(30));
        painelCentral.add(criarCard("Visualizar Faltas de Professores", e -> abrirTelaFaltaProfessores()));
        painelCentral.add(Box.createVerticalStrut(50));
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
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(true);
        botao.addActionListener(acao);

        card.add(botao, BorderLayout.CENTER);
        return card;
    }

    private void abrirTelaUsuarios() {
        new TelaUsuarios(usuarioLogado).setVisible(true);
    }

   private void abrirTelaRelatorioCargaHoraria() {
        new TelaRelatorioCargaHoraria().setVisible(true);
    }

    private void abrirTelaRegistrarCargaComplementar() {
        new TelaRegistrarCargaComplementar().setVisible(true);
    }

    private void abrirTelaFaltaProfessores() {
        new TelaFaltaProfessor().setVisible(true);
    }

    private void sair() {
        dispose();
        new TelaLogin().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario();
            u.setId(2);
            u.setNomeCompleto("Diretor");
            u.setLogin("diretor");
            u.setNivelAcesso(3);
            new TelaDiretor(u).setVisible(true);
        });
    }
}