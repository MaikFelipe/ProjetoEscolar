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

public class TelaSecretarioEscolar extends JFrame {

    private Usuario usuarioLogado;
    private final Color fundoClaro = new Color(245, 245, 245);

    public TelaSecretarioEscolar(Usuario usuarioLogado) {
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

        JLabel mensagem = new JLabel("Bem-vindo, Secretário Escolar " + usuarioLogado.getNomeCompleto(), SwingConstants.CENTER);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 22));
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelSuperior.add(mensagem);
        add(painelSuperior, BorderLayout.NORTH);

        JLabel titulo = new JLabel("Sistema de Gestão Escolar - Secretário Escolar", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.SOUTH);

        JPanel painelCentral = new JPanel(new GridLayout(0, 2, 30, 30));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
        painelCentral.setBackground(fundoClaro);

        painelCentral.add(criarCard("Professores", e -> abrirTelaProfessores()));
        painelCentral.add(criarCard("Cadastrar Turma", e -> abrirTelaTurmas()));
        painelCentral.add(criarCard("Gerenciar Calendário de Aulas", e -> abrirTelaCalendarioAulas()));
        painelCentral.add(criarCard("Matricular Aluno", e -> abrirTelaMatricula()));
        painelCentral.add(criarCard("Solicitar Transferência", e -> abrirTelaSolicitarTransferencia()));
        painelCentral.add(criarCard("Aceitar Transferência", e -> abrirTelaAceitarTransferencia()));
        painelCentral.add(criarCard("Registrar Faltas de Professores", e -> abrirTelaRegistrarFaltaProfessor()));
        painelCentral.add(criarCard("Visualizar Faltas de Professores", e -> abrirTelaFaltaProfessores()));
        painelCentral.add(criarCard("Gerenciar Alunos", e -> abrirTelaAlunos()));
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

    private void abrirTelaProfessores() {
        new TelaProfessores(usuarioLogado).setVisible(true);
    }

    private void abrirTelaTurmas() {
        new TelaTurmas().setVisible(true);
    }

    private void abrirTelaCalendarioAulas() {
        new TelaCalendarioAulas().setVisible(true);
    }

    private void abrirTelaMatricula() {
        new TelaMatricula().setVisible(true);
    }

    private void abrirTelaSolicitarTransferencia() {
        new TelaSolicitarTransferencia(usuarioLogado).setVisible(true);
    }

    private void abrirTelaAceitarTransferencia() {
        new TelaAceitarTransferencia(usuarioLogado).setVisible(true);
    }

    private void abrirTelaRegistrarFaltaProfessor() {
        new TelaRegistrarFaltaProfessor(usuarioLogado).setVisible(true);
    }

    private void abrirTelaFaltaProfessores() {
        new TelaFaltaProfessor(usuarioLogado).setVisible(true);
    }

    private void abrirTelaAlunos() {
        new TelaAlunos().setVisible(true);
    }

    private void sair() {
        dispose();
        new TelaLogin().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario();
            u.setId(4);
            u.setNomeCompleto("Secretário Escolar");
            u.setLogin("secretario");
            u.setNivelAcesso(4);
            new TelaSecretarioEscolar(u).setVisible(true);
        });
    }
}