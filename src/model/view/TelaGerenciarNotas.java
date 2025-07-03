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
import model.controller.CalendarioAulaController;
import model.controller.NotaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaGerenciarNotas extends JFrame {

    private Usuario usuarioLogado;
    private JComboBox<Turma> cbTurma;
    private JComboBox<Disciplina> cbDisciplina;
    private JButton btnCarregar;
    private JTable tabelaNotas;
    private DefaultTableModel modelNotas;

    private CalendarioAulaController calendarioAulaController;
    private NotaController notaController;

    public TelaGerenciarNotas(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.calendarioAulaController = new CalendarioAulaController();
        this.notaController = new NotaController();

        setTitle("Gerenciar Notas - Boletim por Bimestres");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        carregarTurmasEDisciplinasDoProfessor();
    }

    private void initComponents() {
        cbTurma = new JComboBox<>();
        cbDisciplina = new JComboBox<>();
        btnCarregar = new JButton("Carregar");

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelTopo.add(new JLabel("Turma:"));
        painelTopo.add(cbTurma);
        painelTopo.add(new JLabel("Disciplina:"));
        painelTopo.add(cbDisciplina);
        painelTopo.add(btnCarregar);

        modelNotas = new DefaultTableModel(new Object[]{"Aluno", "Bimestre 1", "Bimestre 2", "Bimestre 3", "Bimestre 4"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaNotas = new JTable(modelNotas);
        tabelaNotas.setFillsViewportHeight(true);

        JScrollPane scrollTabela = new JScrollPane(tabelaNotas);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelTopo, BorderLayout.NORTH);
        getContentPane().add(scrollTabela, BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregarNotas());
    }

    private void carregarTurmasEDisciplinasDoProfessor() {
        try {
            List<CalendarioAula> lista = calendarioAulaController.listarPorProfessor(usuarioLogado.getId());

            Map<Integer, Turma> turmasMap = new HashMap<>();
            Map<Integer, Disciplina> disciplinasMap = new HashMap<>();

            for (CalendarioAula ca : lista) {
                turmasMap.put(ca.getTurma().getId(), ca.getTurma());
                disciplinasMap.put(ca.getDisciplina().getId(), ca.getDisciplina());
            }

            cbTurma.removeAllItems();
            turmasMap.values().forEach(cbTurma::addItem);

            cbDisciplina.removeAllItems();
            disciplinasMap.values().forEach(cbDisciplina::addItem);

            if (cbTurma.getItemCount() > 0) {
                cbTurma.setSelectedIndex(0);
            }
            if (cbDisciplina.getItemCount() > 0) {
                cbDisciplina.setSelectedIndex(0);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas e disciplinas: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarNotas() {
        Turma turma = (Turma) cbTurma.getSelectedItem();
        Disciplina disciplina = (Disciplina) cbDisciplina.getSelectedItem();

        if (turma == null || disciplina == null) {
            JOptionPane.showMessageDialog(this, "Selecione turma e disciplina para carregar as notas.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Map<Aluno, Map<Integer, Double>> boletim = notaController.getBoletimPorTurmaEDisciplina(turma.getId(), disciplina.getId());
            modelNotas.setRowCount(0);

            for (Aluno aluno : boletim.keySet()) {
                Map<Integer, Double> notasPorBimestre = boletim.get(aluno);
                Object[] linha = new Object[5];
                linha[0] = aluno.getNomeCompleto();
                for (int b = 1; b <= 4; b++) {
                    Double nota = notasPorBimestre.get(b);
                    linha[b] = nota != null ? nota : "";
                }
                modelNotas.addRow(linha);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar notas: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}