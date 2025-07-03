/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Aluno;
import model.Turma;
import model.Matricula;
import model.controller.AlunoController;
import model.controller.TurmaController;
import model.controller.MatriculaController;
import model.util.Conexao;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.util.List;

public class TelaMatricula extends JFrame {

    private AlunoController alunoController;
    private TurmaController turmaController;
    private MatriculaController matriculaController;

    private JComboBox<Aluno> cbAlunos;
    private JComboBox<Turma> cbTurmas;
    private JComboBox<String> cbStatus;
    private JTextField tfDataMatricula;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnStatusMatriculas;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public TelaMatricula() {
        try {
            Connection conexao = Conexao.getConexao();
            alunoController = new AlunoController();
            turmaController = new TurmaController(conexao);
            matriculaController = new MatriculaController(conexao);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Matricular Aluno");
        setSize(500, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Aluno:"), gbc);
        cbAlunos = new JComboBox<>();
        gbc.gridx = 1;
        add(cbAlunos, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Turma:"), gbc);
        cbTurmas = new JComboBox<>();
        gbc.gridx = 1;
        add(cbTurmas, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Status:"), gbc);
        cbStatus = new JComboBox<>(new String[]{"Matriculado", "Inativo", "Cancelado"});
        gbc.gridx = 1;
        add(cbStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Data Matrícula (dd-MM-yyyy):"), gbc);
        tfDataMatricula = new JTextField(LocalDate.now().format(formatter), 10);
        gbc.gridx = 1;
        add(tfDataMatricula, gbc);

        JPanel panelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnStatusMatriculas = new JButton("Status Matrículas");
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnLimpar);
        panelBotoes.add(btnStatusMatriculas);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(panelBotoes, gbc);

        carregarAlunos();
        carregarTurmas();

        btnSalvar.addActionListener(e -> salvarMatricula());
        btnLimpar.addActionListener(e -> limparFormulario());
        btnStatusMatriculas.addActionListener(e -> abrirTelaStatusMatricula());
    }

    private void carregarAlunos() {
        try {
            List<Aluno> alunos = alunoController.listarAlunos();
            DefaultComboBoxModel<Aluno> model = new DefaultComboBoxModel<>();
            for (Aluno a : alunos) {
                model.addElement(a);
            }
            cbAlunos.setModel(model);
            cbAlunos.setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTurmas() {
        try {
            List<Turma> turmas = turmaController.listarTurmas();
            DefaultComboBoxModel<Turma> model = new DefaultComboBoxModel<>();
            for (Turma t : turmas) {
                model.addElement(t);
            }
            cbTurmas.setModel(model);
            cbTurmas.setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarMatricula() {
        Aluno alunoSelecionado = (Aluno) cbAlunos.getSelectedItem();
        Turma turmaSelecionada = (Turma) cbTurmas.getSelectedItem();
        String statusSelecionado = (String) cbStatus.getSelectedItem();
        String dataStr = tfDataMatricula.getText().trim();

        if (alunoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (turmaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma turma.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate dataMatricula;
        try {
            dataMatricula = LocalDate.parse(dataStr, formatter);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd-MM-yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<Matricula> matriculas = matriculaController.listarTodos();
            boolean jaMatriculado = matriculas.stream().anyMatch(m -> m.getAluno().getId() == alunoSelecionado.getId() && m.getTurma().getId() == turmaSelecionada.getId());
            if (jaMatriculado) {
                JOptionPane.showMessageDialog(this, "Aluno já está matriculado nessa turma.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(alunoSelecionado);
        matricula.setTurma(turmaSelecionada);
        matricula.setDataMatricula(dataMatricula);
        matricula.setStatus(statusSelecionado);

        try {
            matriculaController.salvar(matricula);
            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
            limparFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        cbAlunos.setSelectedIndex(-1);
        cbTurmas.setSelectedIndex(-1);
        cbStatus.setSelectedIndex(0);
        tfDataMatricula.setText(LocalDate.now().format(formatter));
    }

    private void abrirTelaStatusMatricula() {
        new TelaStatusMatricula().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaMatricula().setVisible(true);
        });
    }
}
