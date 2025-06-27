/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Disciplina;
import model.Usuario;
import model.controller.DisciplinaController;
import model.util.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaCadastroDisciplina extends JFrame {

    private Usuario usuarioLogado;
    private DisciplinaController controller;
    private JTable tabelaDisciplinas;
    private DefaultTableModel tabelaModel;
    private JTextField tfNome;
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnVoltar;

    public TelaCadastroDisciplina(Usuario usuario) {
        this.usuarioLogado = usuario;

        try {
            Connection conexao = Conexao.getConexao();
            controller = new DisciplinaController(conexao);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage());
            System.exit(1);
        }

        setTitle("Cadastro de Disciplina");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        carregarDisciplinas();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelForm.add(new JLabel("Nome da Disciplina:"));
        tfNome = new JTextField(30);
        painelForm.add(tfNome);

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

        tabelaModel = new DefaultTableModel(new Object[]{"ID", "Nome"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaDisciplinas = new JTable(tabelaModel);
        tabelaDisciplinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaDisciplinas);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        btnSalvar.addActionListener(e -> salvarDisciplina());
        btnAtualizar.addActionListener(e -> atualizarDisciplina());
        btnExcluir.addActionListener(e -> excluirDisciplina());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal(usuarioLogado).setVisible(true);
        });

        tabelaDisciplinas.getSelectionModel().addListSelectionListener(e -> {
            boolean selecionado = tabelaDisciplinas.getSelectedRow() != -1;
            btnAtualizar.setEnabled(selecionado);
            btnExcluir.setEnabled(selecionado);
            if (selecionado) {
                int linha = tabelaDisciplinas.getSelectedRow();
                String nome = (String) tabelaModel.getValueAt(linha, 1);
                tfNome.setText(nome);
            } else {
                tfNome.setText("");
            }
        });
    }

    private void carregarDisciplinas() {
        try {
            List<Disciplina> disciplinas = controller.listarTodos();
            tabelaModel.setRowCount(0);
            for (Disciplina d : disciplinas) {
                tabelaModel.addRow(new Object[]{d.getId(), d.getNome()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    private void salvarDisciplina() {
        String nome = tfNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da disciplina.");
            return;
        }
        try {
            Disciplina d = new Disciplina();
            d.setNome(nome);
            controller.salvar(d);
            carregarDisciplinas();
            tfNome.setText("");
            JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar disciplina: " + e.getMessage());
        }
    }

    private void atualizarDisciplina() {
        int linha = tabelaDisciplinas.getSelectedRow();
        if (linha == -1) return;
        int id = (int) tabelaModel.getValueAt(linha, 0);
        String nome = tfNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da disciplina.");
            return;
        }
        try {
            Disciplina d = new Disciplina();
            d.setId(id);
            d.setNome(nome);
            controller.salvar(d);
            carregarDisciplinas();
            tfNome.setText("");
            tabelaDisciplinas.clearSelection();
            JOptionPane.showMessageDialog(this, "Disciplina atualizada com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar disciplina: " + e.getMessage());
        }
    }

    private void excluirDisciplina() {
        int linha = tabelaDisciplinas.getSelectedRow();
        if (linha == -1) return;
        int id = (int) tabelaModel.getValueAt(linha, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão da disciplina?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            controller.excluir(id);
            carregarDisciplinas();
            tfNome.setText("");
            tabelaDisciplinas.clearSelection();
            JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir disciplina: " + e.getMessage());
        }
    }
}