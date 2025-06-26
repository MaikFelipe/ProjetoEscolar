package model.view;

import javax.swing.*;
import java.awt.*;
import model.Usuario;

public class TelaPrincipal extends JFrame {

    private Usuario usuarioLogado;
    private JMenuBar menuBar;

    public TelaPrincipal(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        criarMenu();
    }

    private void configurarJanela() {
        setTitle("Sistema Escolar - Usuário: " + usuarioLogado.getNomeCompleto());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarMenu() {
        menuBar = new JMenuBar();

        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuSistema.add(itemSair);

        JMenu menuCadastro = new JMenu("Cadastros");

        JMenuItem itemUsuario = new JMenuItem("Usuário");
        itemUsuario.addActionListener(e -> new TelaCadastroUsuario(usuarioLogado).setVisible(true));

        JMenuItem itemAluno = new JMenuItem("Aluno");
        itemAluno.addActionListener(e -> new TelaCadastroAluno(usuarioLogado).setVisible(true));

        JMenuItem itemProfessor = new JMenuItem("Professor");
        itemProfessor.addActionListener(e -> new TelaCadastroProfessor(usuarioLogado).setVisible(true));

        JMenuItem itemTurma = new JMenuItem("Turma");
        itemTurma.addActionListener(e -> new TelaCadastroTurma(usuarioLogado).setVisible(true));

        JMenuItem itemDisciplina = new JMenuItem("Disciplina");
        itemDisciplina.addActionListener(e -> new TelaCadastroDisciplina(usuarioLogado).setVisible(true));

        JMenuItem itemMatricula = new JMenuItem("Matrícula");
        itemMatricula.addActionListener(e -> new TelaMatriculaAluno(usuarioLogado).setVisible(true));

        if (usuarioLogado.getNivelAcesso().equalsIgnoreCase("SecretarioEducacao")
         || usuarioLogado.getNivelAcesso().equalsIgnoreCase("SuperUsuario")) {
            menuCadastro.add(itemUsuario);
        }

        if (!usuarioLogado.getNivelAcesso().equalsIgnoreCase("Professor")) {
            menuCadastro.add(itemAluno);
            menuCadastro.add(itemProfessor);
            menuCadastro.add(itemTurma);
            menuCadastro.add(itemDisciplina);
            menuCadastro.add(itemMatricula);
        }

        menuBar.add(menuSistema);
        menuBar.add(menuCadastro);
        setJMenuBar(menuBar);
    }
}