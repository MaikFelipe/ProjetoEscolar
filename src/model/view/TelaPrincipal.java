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
        criarPainelCentral();
    }

    private void configurarJanela() {
        setUndecorated(true); // Remove barra de título e bordas
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
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

        JMenuItem itemTransferencia = new JMenuItem("Transferência de Aluno");
        itemTransferencia.addActionListener(e -> new TelaTransferenciaAluno(usuarioLogado).setVisible(true));

        JMenuItem itemEscola = new JMenuItem("Escola");
        itemEscola.addActionListener(e -> new TelaCadastroEscola().setVisible(true));

        if (usuarioLogado.getNivelAcesso() != null &&
            (usuarioLogado.getNivelAcesso().equalsIgnoreCase("SecretarioEducacao") ||
             usuarioLogado.getNivelAcesso().equalsIgnoreCase("SuperUsuario"))) {
        }

        if (usuarioLogado.getNivelAcesso() != null &&
            !usuarioLogado.getNivelAcesso().equalsIgnoreCase("Professor")) {
            menuCadastro.add(itemAluno);
            menuCadastro.add(itemProfessor);
            menuCadastro.add(itemTurma);
            menuCadastro.add(itemDisciplina);
            menuCadastro.add(itemMatricula);
            menuCadastro.add(itemTransferencia);
            menuCadastro.add(itemEscola);
        }

        JMenu menuConsultas = new JMenu("Consultas");
        JMenuItem itemVisualizarFrequencia = new JMenuItem("Visualizar Frequência de Aluno");
        itemVisualizarFrequencia.addActionListener(e -> new TelaVisualizarFrequenciaAluno().setVisible(true));
        menuConsultas.add(itemVisualizarFrequencia);

        menuBar.add(menuSistema);
        menuBar.add(menuCadastro);
        menuBar.add(menuConsultas);

        setJMenuBar(menuBar);
    }

    private void criarPainelCentral() {
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        Color corPadrao = new Color(173, 216, 230); // Azul claro

        int col = 0;
        int row = 0;

        if (usuarioLogado.getNivelAcesso() != null &&
            !usuarioLogado.getNivelAcesso().equalsIgnoreCase("Professor")) {

            painelCentral.add(criarCard("Cadastro de Aluno", corPadrao, e -> new TelaCadastroAluno(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Cadastro de Professor", corPadrao, e -> new TelaCadastroProfessor(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Cadastro de Turma", corPadrao, e -> new TelaCadastroTurma(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));

            col = 0; row++;
            painelCentral.add(criarCard("Cadastro de Disciplina", corPadrao, e -> new TelaCadastroDisciplina(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Matrícula", corPadrao, e -> new TelaMatriculaAluno(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Transferência de Aluno", corPadrao, e -> new TelaTransferenciaAluno(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));

            col = 0; row++;
            painelCentral.add(criarCard("Lançar Notas", corPadrao, e -> new TelaLancarNotas().setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Registrar Frequência", corPadrao, e -> new TelaRegistrarFrequencia(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Faltas de Professores", corPadrao, e -> new TelaFaltaProfessor(usuarioLogado).setVisible(true)), setGbcCard(gbc, col++, row));

            col = 0; row++;
            painelCentral.add(criarCard("Visualizar Frequência", corPadrao, e -> new TelaVisualizarFrequenciaAluno().setVisible(true)), setGbcCard(gbc, col++, row));
            painelCentral.add(criarCard("Cadastro de Escola", corPadrao, e -> new TelaCadastroEscola().setVisible(true)), setGbcCard(gbc, col++, row));
        }

        painelCentral.add(criarCard("Sair do Sistema", corPadrao, e -> System.exit(0)), setGbcCard(gbc, 0, ++row));

        add(new JScrollPane(painelCentral), BorderLayout.CENTER);
    }

    private JPanel criarCard(String titulo, Color corFundo, java.awt.event.ActionListener action) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(250, 120));
        card.setBackground(corFundo);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setLayout(new BorderLayout());

        JLabel labelTitulo = new JLabel("<html><center>" + titulo + "</center></html>", SwingConstants.CENTER);
        labelTitulo.setForeground(Color.BLACK);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        card.add(labelTitulo, BorderLayout.CENTER);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(null);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(corFundo.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(corFundo);
            }
        });

        return card;
    }

    private GridBagConstraints setGbcCard(GridBagConstraints gbc, int x, int y) {
        GridBagConstraints newGbc = (GridBagConstraints) gbc.clone();
        newGbc.gridx = x;
        newGbc.gridy = y;
        return newGbc;
    }
}
