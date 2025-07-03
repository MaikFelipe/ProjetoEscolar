/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Escola;
import model.Professor;
import model.Turma;
import model.controller.EscolaController;
import model.controller.ProfessorController;
import model.controller.TurmaController;
import model.util.Conexao;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaCadastroTurma extends JFrame {

    private JTextField tfNome, tfSerie, tfNivelEnsino, tfAnoLetivo, tfTurno, tfNumMinAlunos, tfNumMaxAlunos;
    private JComboBox<Escola> cbEscola;
    private JComboBox<Professor> cbProfessor;
    private JButton btnSalvar, btnCancelar;
    private Turma turma;
    private TurmaController controller;
    private EscolaController escolaController;
    private ProfessorController professorController;
    private TelaTurmas telaTurmasPai;

    public TelaCadastroTurma(TelaTurmas telaTurmasPai, Turma turma) {
        this.telaTurmasPai = telaTurmasPai;
        this.turma = turma;
        setTitle(turma == null ? "Cadastrar Turma" : "Editar Turma");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        try {
            Connection conn = Conexao.getConexao();
            escolaController = new EscolaController(conn);
            professorController = new ProfessorController();
            controller = new TurmaController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco: " + e.getMessage());
            dispose();
            return;
        }

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Nome:", "Série:", "Nível de Ensino:", "Ano Letivo:", "Turno:",
                "Número Mínimo Alunos:", "Número Máximo Alunos:", "Escola:", "Professor Responsável:"};
        Component[] componentes = new Component[9];

        tfNome = new JTextField();
        tfSerie = new JTextField();
        tfNivelEnsino = new JTextField();
        tfAnoLetivo = new JTextField();
        tfTurno = new JTextField();
        tfNumMinAlunos = new JTextField();
        tfNumMaxAlunos = new JTextField();
        cbEscola = new JComboBox<>();
        cbProfessor = new JComboBox<>();

        componentes[0] = tfNome;
        componentes[1] = tfSerie;
        componentes[2] = tfNivelEnsino;
        componentes[3] = tfAnoLetivo;
        componentes[4] = tfTurno;
        componentes[5] = tfNumMinAlunos;
        componentes[6] = tfNumMaxAlunos;
        componentes[7] = cbEscola;
        componentes[8] = cbProfessor;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            painelCampos.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            painelCampos.add(componentes[i], gbc);
        }

        add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarComboEscolas();
        carregarComboProfessores();
        if (turma != null) carregarCampos();

        btnSalvar.addActionListener(e -> salvarTurma());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarComboEscolas() {
        try {
            List<Escola> escolas = escolaController.listarTodos();
            DefaultComboBoxModel<Escola> modelEscola = new DefaultComboBoxModel<>();
            for (Escola e : escolas) {
                modelEscola.addElement(e);
            }
            cbEscola.setModel(modelEscola);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar escolas: " + e.getMessage());
        }
    }

    private void carregarComboProfessores() {
        try {
            List<Professor> professores = professorController.listarProfessores();
            DefaultComboBoxModel<Professor> modelProf = new DefaultComboBoxModel<>();
            modelProf.addElement(null);
            for (Professor p : professores) {
                modelProf.addElement(p);
            }
            cbProfessor.setModel(modelProf);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar professores: " + e.getMessage());
        }
    }

    private void carregarCampos() {
        tfNome.setText(turma.getNome());
        tfSerie.setText(turma.getSerie());
        tfNivelEnsino.setText(turma.getNivelEnsino());
        tfAnoLetivo.setText(String.valueOf(turma.getAnoLetivo()));
        tfTurno.setText(turma.getTurno());
        tfNumMinAlunos.setText(String.valueOf(turma.getNumeroMinimoAlunos()));
        tfNumMaxAlunos.setText(String.valueOf(turma.getNumeroMaximoAlunos()));
        cbEscola.setSelectedItem(turma.getEscola());
        cbProfessor.setSelectedItem(turma.getProfessorResponsavel());
    }

    private void salvarTurma() {
        try {
            String nome = tfNome.getText().trim();
            String serie = tfSerie.getText().trim();
            String nivelEnsino = tfNivelEnsino.getText().trim();
            int anoLetivo = Integer.parseInt(tfAnoLetivo.getText().trim());
            String turno = tfTurno.getText().trim();
            int numMin = Integer.parseInt(tfNumMinAlunos.getText().trim());
            int numMax = Integer.parseInt(tfNumMaxAlunos.getText().trim());
            Escola escola = (Escola) cbEscola.getSelectedItem();
            Professor professor = (Professor) cbProfessor.getSelectedItem();

            if (nome.isEmpty() || serie.isEmpty() || nivelEnsino.isEmpty() || turno.isEmpty() || escola == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }
            if (numMin < 0 || numMax < numMin) {
                JOptionPane.showMessageDialog(this, "Valores inválidos para números de alunos.");
                return;
            }

            if (turma == null) {
                turma = new Turma();
            }

            turma.setNome(nome);
            turma.setSerie(serie);
            turma.setNivelEnsino(nivelEnsino);
            turma.setAnoLetivo(anoLetivo);
            turma.setTurno(turno);
            turma.setNumeroMinimoAlunos(numMin);
            turma.setNumeroMaximoAlunos(numMax);
            turma.setEscola(escola);
            turma.setProfessorResponsavel(professor);

            String resultado;
            if (turma.getId() == 0) {
                resultado = controller.cadastrarTurma(turma);
            } else {
                resultado = controller.atualizarTurma(turma);
            }
            JOptionPane.showMessageDialog(this, resultado);

            telaTurmasPai.carregarTabela();
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano letivo, número mínimo e máximo devem ser números válidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar turma: " + e.getMessage());
        }
    }
}
