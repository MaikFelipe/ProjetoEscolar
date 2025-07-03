/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Matricula;
import model.controller.MatriculaController;
import model.Aluno;
import model.controller.AlunoController;
import model.Turma;
import model.controller.TurmaController;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class TelaStatusMatricula extends JFrame {

    private MatriculaController matriculaController;
    private AlunoController alunoController;
    private TurmaController turmaController;

    private JTable tableMatriculas;
    private JComboBox<String> cbStatusFiltro;
    private JButton btnAtualizarStatus;
    private JButton btnFechar;

    private DefaultTableModel tableModel;

    public TelaStatusMatricula() {
        try {
            Connection conexao = Conexao.getConexao();
            matriculaController = new MatriculaController(conexao);
            alunoController = new AlunoController();
            turmaController = new TurmaController(conexao);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Status das Matrículas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Filtro Status
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Filtrar por status: "));
        cbStatusFiltro = new JComboBox<>(new String[]{"Todos", "Matriculado", "Inativo", "Cancelado"});
        painelFiltro.add(cbStatusFiltro);
        add(painelFiltro, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Aluno", "Turma", "Data Matrícula", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };
        tableMatriculas = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tableMatriculas);
        add(scroll, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        btnAtualizarStatus = new JButton("Alterar Status");
        btnFechar = new JButton("Fechar");
        painelBotoes.add(btnAtualizarStatus);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarTabela();

        cbStatusFiltro.addActionListener(e -> carregarTabela());

        btnAtualizarStatus.addActionListener(e -> alterarStatusSelecionado());

        btnFechar.addActionListener(e -> dispose());
    }

    private void carregarTabela() {
        try {
            List<Matricula> todasMatriculas = matriculaController.listarTodos();
            String filtro = (String) cbStatusFiltro.getSelectedItem();

            List<Matricula> filtradas = todasMatriculas.stream()
                    .filter(m -> filtro.equals("Todos") || m.getStatus().equalsIgnoreCase(filtro))
                    .collect(Collectors.toList());

            tableModel.setRowCount(0);
            for (Matricula m : filtradas) {
                Aluno a = alunoController.buscarAluno(m.getAluno().getId());
                Turma t = turmaController.buscarTurmaPorId(m.getTurma().getId());
                String dataStr = m.getDataMatricula().toString();
                tableModel.addRow(new Object[]{m.getId(), a != null ? a.getNomeCompleto() : "N/A", t != null ? t.getNome() : "N/A", dataStr, m.getStatus()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar matrículas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarStatusSelecionado() {
        int linha = tableMatriculas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para alterar o status.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMatricula = (int) tableModel.getValueAt(linha, 0);

        String[] opcoes = {"Matriculado", "Inativo", "Cancelado"};
        String novoStatus = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o novo status:",
                "Alterar Status da Matrícula",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                tableModel.getValueAt(linha, 4)
        );

        if (novoStatus != null && !novoStatus.isEmpty()) {
            try {
                Matricula m = matriculaController.buscar(idMatricula);
                if (m != null) {
                    m.setStatus(novoStatus);
                    matriculaController.salvar(m);
                    carregarTabela();
                    JOptionPane.showMessageDialog(this, "Status atualizado com sucesso.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaStatusMatricula tela = new TelaStatusMatricula();
            tela.setVisible(true);
        });
    }
}