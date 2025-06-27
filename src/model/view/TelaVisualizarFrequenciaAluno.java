package model.view;

import model.controller.FrequenciaController;
import model.dao.AlunoDAO;
import model.Aluno;
import model.Frequencia;
import model.Turma;
import java.sql.Connection;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaVisualizarFrequenciaAluno extends JFrame {

    private JComboBox<Aluno> comboAlunos;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnBuscar;

    public TelaVisualizarFrequenciaAluno() {
    setTitle("Visualizar Frequência dos Alunos");
    setSize(800, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel painelTopo = new JPanel();

    try {
        Connection conn = model.util.Conexao.getConexao();
        AlunoDAO alunoDAO = new AlunoDAO(conn);
        List<Aluno> listaAlunos = alunoDAO.listarTodos();
        comboAlunos = new JComboBox<>(listaAlunos.toArray(new Aluno[0]));
    } catch (Exception e) {
        e.printStackTrace();
        comboAlunos = new JComboBox<>();
        JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + e.getMessage());
    }

    btnBuscar = new JButton("Buscar Frequência");
    painelTopo.add(new JLabel("Aluno:"));
    painelTopo.add(comboAlunos);
    painelTopo.add(btnBuscar);
    add(painelTopo, BorderLayout.NORTH);

    modeloTabela = new DefaultTableModel(new String[]{"Data", "Turma", "Presença", "Faltas Acumuladas"}, 0);
    tabela = new JTable(modeloTabela);
    add(new JScrollPane(tabela), BorderLayout.CENTER);

    btnBuscar.addActionListener(e -> carregarFrequencias());
}

    private void carregarFrequencias() {
        modeloTabela.setRowCount(0);

        Aluno alunoSelecionado = (Aluno) comboAlunos.getSelectedItem();
        if (alunoSelecionado != null) {
            FrequenciaController controller = new FrequenciaController();
            List<Frequencia> lista = controller.listarFrequenciasPorAluno(alunoSelecionado.getId());

            if (lista == null || lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma frequência encontrada para este aluno.");
                return;
            }

            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Frequencia f : lista) {
                Turma turma = f.getTurma();
                modeloTabela.addRow(new Object[]{
                        f.getData().format(formatoData),
                        (turma != null ? "Turma ID: " + turma.getId() : "N/A"),
                        (f.isPresente() ? "Presente" : "Faltou"),
                        f.getTotalFaltasAcumuladas()
                });
            }
        }
    }
}
