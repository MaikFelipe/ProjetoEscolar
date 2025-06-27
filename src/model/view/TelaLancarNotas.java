/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.*;
import model.controller.NotaController;
import model.dao.AlunoDAO;
import model.dao.DisciplinaDAO;
import model.dao.TurmaDAO;
import model.util.Conexao;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class TelaLancarNotas extends JFrame {

    private JComboBox<Aluno> cbAluno;
    private JComboBox<Disciplina> cbDisciplina;
    private JComboBox<Turma> cbTurma;
    private JComboBox<Integer> cbBimestre;
    private JTextField tfNota;
    private JButton btnSalvar, btnVoltar;

    private NotaController notaController;

    public TelaLancarNotas() {
        notaController = new NotaController();
        setTitle("Lançar Notas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        cbAluno = new JComboBox<>();
        cbDisciplina = new JComboBox<>();
        cbTurma = new JComboBox<>();
        cbBimestre = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        tfNota = new JTextField(5);
        btnSalvar = new JButton("Salvar Nota");
        btnVoltar = new JButton("Voltar");

        carregarDados();

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        panel.add(cbAluno, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        panel.add(cbDisciplina, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        panel.add(cbTurma, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Bimestre:"), gbc);
        gbc.gridx = 1;
        panel.add(cbBimestre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        panel.add(tfNota, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(btnSalvar, gbc);
        gbc.gridx = 1;
        panel.add(btnVoltar, gbc);

        add(panel);

        btnSalvar.addActionListener(e -> salvarNota());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarDados() {
        try (Connection conn = Conexao.getConexao()) {
            AlunoDAO alunoDAO = new AlunoDAO(conn);
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conn);
            TurmaDAO turmaDAO = new TurmaDAO();

            cbAluno.removeAllItems();
            for (Aluno a : alunoDAO.listarTodos()) cbAluno.addItem(a);

            cbDisciplina.removeAllItems();
            for (Disciplina d : disciplinaDAO.listarTodos()) cbDisciplina.addItem(d);

            cbTurma.removeAllItems();
            for (Turma t : turmaDAO.listarTodas()) cbTurma.addItem(t);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void salvarNota() {
        try {
            Aluno aluno = (Aluno) cbAluno.getSelectedItem();
            Disciplina disciplina = (Disciplina) cbDisciplina.getSelectedItem();
            Turma turma = (Turma) cbTurma.getSelectedItem();
            int bimestre = (int) cbBimestre.getSelectedItem();
            double valorNota = Double.parseDouble(tfNota.getText());

            Nota nota = new Nota();
            nota.setAluno(aluno);
            nota.setDisciplina(disciplina);
            nota.setTurma(turma);
            nota.setBimestre(bimestre);
            nota.setNota(valorNota);

            String resposta = notaController.cadastrarNota(nota);
            JOptionPane.showMessageDialog(this, resposta);
            tfNota.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nota inválida. Digite um número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar nota: " + ex.getMessage());
        }
    }
}