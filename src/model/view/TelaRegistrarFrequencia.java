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
import model.controller.FrequenciaController;
import model.dao.AlunoDAO;
import model.dao.TurmaDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaRegistrarFrequencia extends JFrame {

    private JComboBox<Turma> comboTurmas;
    private JTable tabelaAlunos;
    private JButton btnRegistrar;
    private FrequenciaController controller;

    public TelaRegistrarFrequencia(Usuario usuarioLogado) {
        controller = new FrequenciaController();
        setTitle("Registrar Frequência");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        comboTurmas = new JComboBox<>();
        carregarTurmas();
        comboTurmas.addActionListener(e -> carregarAlunos());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(new JLabel("Selecione a Turma:"));
        painelTopo.add(comboTurmas);

        tabelaAlunos = new JTable(new DefaultTableModel(new Object[]{"ID", "Nome do Aluno", "Presente"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Somente checkbox editável
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaAlunos);

        btnRegistrar = new JButton("Registrar Frequência");
        btnRegistrar.addActionListener(e -> registrarFrequencia());

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(btnRegistrar, BorderLayout.SOUTH);

        setContentPane(painel);
        setVisible(true);
    }

    private void carregarTurmas() {
        try {
            TurmaDAO turmaDAO = new TurmaDAO();
            List<Turma> turmas = turmaDAO.listarTodas();
            for (Turma t : turmas) {
                comboTurmas.addItem(t);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas: " + e.getMessage());
        }
    }

    private void carregarAlunos() {
        DefaultTableModel model = (DefaultTableModel) tabelaAlunos.getModel();
        model.setRowCount(0);

        Turma turmaSelecionada = (Turma) comboTurmas.getSelectedItem();
        if (turmaSelecionada != null) {
            try {
                AlunoDAO alunoDAO = new AlunoDAO();
                List<Aluno> alunos = alunoDAO.listarTodos(); // opcional: filtrar por turma
                for (Aluno aluno : alunos) {
                    model.addRow(new Object[]{aluno.getId(), aluno.getNomeCompleto(), Boolean.TRUE});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + e.getMessage());
            }
        }
    }

    private void registrarFrequencia() {
        Turma turma = (Turma) comboTurmas.getSelectedItem();
        if (turma == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma turma.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tabelaAlunos.getModel();
        LocalDate dataHoje = LocalDate.now();
        int registros = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            int alunoId = (int) model.getValueAt(i, 0);
            boolean presente = (boolean) model.getValueAt(i, 2);

            Aluno aluno = new Aluno();
            aluno.setId(alunoId);

            Frequencia f = new Frequencia();
            f.setAluno(aluno);
            f.setTurma(turma);
            f.setData(dataHoje);
            f.setPresente(presente);
            f.setTotalFaltasAcumuladas(presente ? 0 : 1); // básico, pode ser melhorado

            String msg = controller.cadastrarFrequencia(f);
            System.out.println(msg);
            registros++;
        }

        JOptionPane.showMessageDialog(this, "Frequência registrada para " + registros + " alunos.");
        dispose();
    }
}
