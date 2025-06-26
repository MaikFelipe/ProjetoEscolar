/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Turma;
import model.controller.TurmaController;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaCadastroTurma extends JFrame {

    private Usuario usuarioLogado;
    private TurmaController controller;
    private JTable tabelaTurmas;
    private DefaultTableModel tabelaModel;
    private JTextField tfNome, tfSerie, tfNivelEnsino, tfAnoLetivo, tfTurno, tfNumMinAlunos, tfNumMaxAlunos;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnVoltar;

    public TelaCadastroTurma(Usuario usuario) {
        this.usuarioLogado = usuario;
        controller = new TurmaController();
        setTitle("Cadastro de Turma");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        carregarTurmas();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelForm.add(new JLabel("Nome:"));
        tfNome = new JTextField(15);
        painelForm.add(tfNome);

        painelForm.add(new JLabel("Série:"));
        tfSerie = new JTextField(10);
        painelForm.add(tfSerie);

        painelForm.add(new JLabel("Nível de Ensino:"));
        tfNivelEnsino = new JTextField(15);
        painelForm.add(tfNivelEnsino);

        painelForm.add(new JLabel("Ano Letivo:"));
        tfAnoLetivo = new JTextField(5);
        painelForm.add(tfAnoLetivo);

        painelForm.add(new JLabel("Turno:"));
        tfTurno = new JTextField(10);
        painelForm.add(tfTurno);

        painelForm.add(new JLabel("Nº Mínimo Alunos:"));
        tfNumMinAlunos = new JTextField(5);
        painelForm.add(tfNumMinAlunos);

        painelForm.add(new JLabel("Nº Máximo Alunos:"));
        tfNumMaxAlunos = new JTextField(5);
        painelForm.add(tfNumMaxAlunos);

        btnSalvar = new JButton("Salvar");
        painelForm.add(btnSalvar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setEnabled(false);
        painelForm.add(btnAtualizar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setEnabled(false);
        painelForm.add(btnExcluir);

        btnVoltar = new JButton("Voltar");
        painelForm.add(btnVoltar);

        painelPrincipal.add(painelForm, BorderLayout.NORTH);

        tabelaModel = new DefaultTableModel(new Object[]{
                "ID", "Nome", "Série", "Nível Ensino", "Ano Letivo", "Turno", "Min. Alunos", "Max. Alunos"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaTurmas = new JTable(tabelaModel);
        tabelaTurmas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaTurmas);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        btnSalvar.addActionListener(e -> salvarTurma());
        btnAtualizar.addActionListener(e -> atualizarTurma());
        btnExcluir.addActionListener(e -> excluirTurma());
        btnVoltar.addActionListener(e -> voltar());

        tabelaTurmas.getSelectionModel().addListSelectionListener(e -> {
            boolean selecionado = tabelaTurmas.getSelectedRow() != -1;
            btnAtualizar.setEnabled(selecionado);
            btnExcluir.setEnabled(selecionado);
            if (selecionado) {
                int linha = tabelaTurmas.getSelectedRow();
                tfNome.setText((String) tabelaModel.getValueAt(linha, 1));
                tfSerie.setText((String) tabelaModel.getValueAt(linha, 2));
                tfNivelEnsino.setText((String) tabelaModel.getValueAt(linha, 3));
                tfAnoLetivo.setText(String.valueOf(tabelaModel.getValueAt(linha, 4)));
                tfTurno.setText((String) tabelaModel.getValueAt(linha, 5));
                tfNumMinAlunos.setText(String.valueOf(tabelaModel.getValueAt(linha, 6)));
                tfNumMaxAlunos.setText(String.valueOf(tabelaModel.getValueAt(linha, 7)));
            } else {
                limparCampos();
            }
        });
    }

    private void carregarTurmas() {
        List<Turma> turmas = controller.listarTurmas();
        tabelaModel.setRowCount(0);
        if (turmas != null) {
            for (Turma t : turmas) {
                tabelaModel.addRow(new Object[]{
                        t.getId(),
                        t.getNome(),
                        t.getSerie(),
                        t.getNivelEnsino(),
                        t.getAnoLetivo(),
                        t.getTurno(),
                        t.getNumeroMinimoAlunos(),
                        t.getNumeroMaximoAlunos()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas.");
        }
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

            if (nome.isEmpty() || serie.isEmpty() || nivelEnsino.isEmpty() || turno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }

            Turma turma = new Turma(0, nome, serie, nivelEnsino, anoLetivo, turno, numMin, numMax);
            String msg = controller.cadastrarTurma(turma);
            JOptionPane.showMessageDialog(this, msg);
            carregarTurmas();
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano Letivo, Nº Mínimo e Nº Máximo devem ser números válidos.");
        }
    }

    private void atualizarTurma() {
        int linha = tabelaTurmas.getSelectedRow();
        if (linha == -1) return;

        try {
            int id = (int) tabelaModel.getValueAt(linha, 0);
            String nome = tfNome.getText().trim();
            String serie = tfSerie.getText().trim();
            String nivelEnsino = tfNivelEnsino.getText().trim();
            int anoLetivo = Integer.parseInt(tfAnoLetivo.getText().trim());
            String turno = tfTurno.getText().trim();
            int numMin = Integer.parseInt(tfNumMinAlunos.getText().trim());
            int numMax = Integer.parseInt(tfNumMaxAlunos.getText().trim());

            if (nome.isEmpty() || serie.isEmpty() || nivelEnsino.isEmpty() || turno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }

            Turma turma = new Turma(id, nome, serie, nivelEnsino, anoLetivo, turno, numMin, numMax);
            String msg = controller.atualizarTurma(turma);
            JOptionPane.showMessageDialog(this, msg);
            carregarTurmas();
            limparCampos();
            tabelaTurmas.clearSelection();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano Letivo, Nº Mínimo e Nº Máximo devem ser números válidos.");
        }
    }

    private void excluirTurma() {
        int linha = tabelaTurmas.getSelectedRow();
        if (linha == -1) return;

        int id = (int) tabelaModel.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão da turma?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String msg = controller.excluirTurma(id);
        JOptionPane.showMessageDialog(this, msg);
        carregarTurmas();
        limparCampos();
        tabelaTurmas.clearSelection();
    }

    private void limparCampos() {
        tfNome.setText("");
        tfSerie.setText("");
        tfNivelEnsino.setText("");
        tfAnoLetivo.setText("");
        tfTurno.setText("");
        tfNumMinAlunos.setText("");
        tfNumMaxAlunos.setText("");
    }

    private void voltar() {
        dispose();
        new TelaPrincipal(usuarioLogado).setVisible(true);
    }
}