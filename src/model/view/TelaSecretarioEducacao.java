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

public class TelaSecretarioEducacao extends JFrame {

    private Usuario usuarioLogado;
    private JButton btnUsuarios, btnEscolas, btnRelatorios, btnSair;

    public TelaSecretarioEducacao(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Painel do Secretário de Educação");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Gestão Escolar - Secretário de Educação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(4, 1, 20, 20));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        btnUsuarios = new JButton("Gerenciar Usuários");
        btnEscolas = new JButton("Gerenciar Escolas");
        btnRelatorios = new JButton("Relatórios");
        btnSair = new JButton("Sair");

        painelCentral.add(btnUsuarios);
        painelCentral.add(btnEscolas);
        painelCentral.add(btnRelatorios);
        painelCentral.add(btnSair);

        add(painelCentral, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> abrirTelaUsuarios());
        btnEscolas.addActionListener(e -> abrirTelaEscolas());
        btnRelatorios.addActionListener(e -> abrirTelaRelatorios());
        btnSair.addActionListener(e -> sair());
    }

    private void abrirTelaUsuarios() {
        new TelaUsuarios(usuarioLogado).setVisible(true);
    }

    private void abrirTelaEscolas() {
        JOptionPane.showMessageDialog(this, "Tela de Escolas ainda não implementada.");
    }

    private void abrirTelaRelatorios() {
        JOptionPane.showMessageDialog(this, "Tela de Relatórios ainda não implementada.");
    }

    private void sair() {
        dispose();
        new TelaLogin().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario();
            u.setId(1);
            u.setNomeCompleto("Secretário");
            u.setLogin("secretario");
            u.setNivelAcesso(1);
            new TelaSecretarioEducacao(u).setVisible(true);
        });
    }
}