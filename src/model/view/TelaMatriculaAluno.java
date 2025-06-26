package model.view;

import model.Aluno;
import model.Turma;
import model.Matricula;
import model.controller.AlunoController;
import model.controller.TurmaController;
import model.controller.MatriculaController;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaMatriculaAluno extends JFrame {

    private Usuario usuarioLogado;

    private JComboBox<Aluno> cbAlunos;
    private JComboBox<Turma> cbTurmas;
    private JTextField tfDataMatricula;
    private JComboBox<String> cbStatus;

    private JButton btnSalvar;
    private JButton btnVoltar;

    private JTable tabelaMatriculas;
    private DefaultTableModel tabelaModel;

    private AlunoController alunoController;
    private TurmaController turmaController;
    private MatriculaController matriculaController;

    public TelaMatriculaAluno(Usuario usuario) {
        this.usuarioLogado = usuario;
        alunoController = new AlunoController();
        turmaController = new TurmaController();
        matriculaController = new MatriculaController();

        configurarJanela();
        criarComponentes();
        carregarDados();
        carregarMatriculas();
    }

    private void configurarJanela() {
        setTitle("Matrícula de Aluno - Usuário: " + usuarioLogado.getNomeCompleto());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Painel formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Aluno
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Aluno:"), gbc);
        cbAlunos = new JComboBox<>();
        gbc.gridx = 1;
        painelFormulario.add(cbAlunos, gbc);

        // Turma
        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Turma:"), gbc);
        cbTurmas = new JComboBox<>();
        gbc.gridx = 1;
        painelFormulario.add(cbTurmas, gbc);

        // Data Matrícula
        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Data Matrícula (yyyy-MM-dd):"), gbc);
        tfDataMatricula = new JTextField(15);
        gbc.gridx = 1;
        painelFormulario.add(tfDataMatricula, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        painelFormulario.add(new JLabel("Status:"), gbc);
        cbStatus = new JComboBox<>(new String[] {"Ativo", "Inativo", "Cancelado"});
        gbc.gridx = 1;
        painelFormulario.add(cbStatus, gbc);

        // Botões
        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnVoltar = new JButton("Voltar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        painelFormulario.add(painelBotoes, gbc);

        // Tabela de matrículas
        tabelaModel = new DefaultTableModel(new String[] {
                "ID", "Aluno", "Turma", "Data Matrícula", "Status"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaMatriculas = new JTable(tabelaModel);
        JScrollPane spTabela = new JScrollPane(tabelaMatriculas);

        painel.add(painelFormulario, BorderLayout.NORTH);
        painel.add(spTabela, BorderLayout.CENTER);

        add(painel);

        // Listeners
        btnSalvar.addActionListener(e -> salvarMatricula());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal(usuarioLogado).setVisible(true);
        });
    }

    private void carregarDados() {
        try {
            List<Aluno> alunos = alunoController.listarAlunos();
            cbAlunos.removeAllItems();
            if (alunos != null) {
                for (Aluno a : alunos) {
                    cbAlunos.addItem(a);
                }
            }

            List<Turma> turmas = turmaController.listarTurmas();
            cbTurmas.removeAllItems();
            if (turmas != null) {
                for (Turma t : turmas) {
                    cbTurmas.addItem(t);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos ou turmas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarMatriculas() {
        try {
            List<Matricula> matriculas = matriculaController.listarMatriculas();
            tabelaModel.setRowCount(0);
            if (matriculas != null) {
                for (Matricula m : matriculas) {
                    tabelaModel.addRow(new Object[] {
                            m.getId(),
                            m.getAluno().getNomeCompleto(),
                            m.getTurma().getNome(),
                            m.getDataMatricula(),
                            m.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar matrículas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarMatricula() {
        Aluno aluno = (Aluno) cbAlunos.getSelectedItem();
        Turma turma = (Turma) cbTurmas.getSelectedItem();
        String dataStr = tfDataMatricula.getText().trim();
        String status = (String) cbStatus.getSelectedItem();

        if (aluno == null || turma == null || dataStr.isEmpty() || status == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate dataMatricula;
        try {
            dataMatricula = LocalDate.parse(dataStr);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setDataMatricula(dataMatricula);
        matricula.setStatus(status);

        String resultado = matriculaController.cadastrarMatricula(matricula);
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.contains("sucesso")) {
            carregarMatriculas();
            limparCampos();
        }
    }

    private void limparCampos() {
        tfDataMatricula.setText("");
        cbStatus.setSelectedIndex(0);
        if (cbAlunos.getItemCount() > 0) cbAlunos.setSelectedIndex(0);
        if (cbTurmas.getItemCount() > 0) cbTurmas.setSelectedIndex(0);
    }

    // Para melhorar a exibição dos objetos no JComboBox
    static {
        UIManager.put("ComboBox.renderer", new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Aluno) {
                    value = ((Aluno) value).getNomeCompleto();
                } else if (value instanceof Turma) {
                    value = ((Turma) value).getNome();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario usuarioTeste = new Usuario();
            usuarioTeste.setNomeCompleto("Usuário Teste");
            new TelaMatriculaAluno(usuarioTeste).setVisible(true);
        });
    }
}