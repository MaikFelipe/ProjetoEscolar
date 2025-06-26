/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Professor;
import model.Usuario;
import model.controller.ProfessorController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaCadastroProfessor extends JFrame {

    private JTextField tfNome, tfCpf, tfEmail, tfTelefone;
    private JTextArea taObservacoes;
    private JButton btnSalvar, btnAtualizar, btnExcluir, btnVoltar;
    private JTable tabelaProfessores;
    private DefaultTableModel tabelaModel;
    private ProfessorController controller;
    private int idSelecionado = -1;
    private Usuario usuarioLogado;

    public TelaCadastroProfessor(Usuario usuario) {
        this.usuarioLogado = usuario;
        controller = new ProfessorController();
        setTitle("Cadastro de Professor");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        carregarProfessores();
        setVisible(true);
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfNome = new JTextField(30);
        tfCpf = new JTextField(15);
        tfEmail = new JTextField(30);
        tfTelefone = new JTextField(15);
        taObservacoes = new JTextArea(5, 30);
        JScrollPane spObservacoes = new JScrollPane(taObservacoes);

        gbc.gridx = 0; gbc.gridy = 0; painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; painelFormulario.add(tfNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; painelFormulario.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; painelFormulario.add(tfCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2; painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; painelFormulario.add(tfEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; painelFormulario.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; painelFormulario.add(tfTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 4; painelFormulario.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1; painelFormulario.add(spObservacoes, gbc);

        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);

        tabelaModel = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Email", "Telefone", "Observações"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelaProfessores = new JTable(tabelaModel);
        tabelaProfessores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane spTabela = new JScrollPane(tabelaProfessores);

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(spTabela, BorderLayout.SOUTH);
        add(painelPrincipal);

        btnSalvar.addActionListener(e -> salvarProfessor());
        btnAtualizar.addActionListener(e -> atualizarProfessor());
        btnExcluir.addActionListener(e -> excluirProfessor());
        btnVoltar.addActionListener(e -> voltar());

        tabelaProfessores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarProfessorSelecionado();
        });
    }

    private void salvarProfessor() {
        try {
            Professor p = new Professor();
            p.setNome(tfNome.getText().trim());
            p.setCpf(tfCpf.getText().trim());
            p.setEmail(tfEmail.getText().trim());
            p.setTelefone(tfTelefone.getText().trim());
            p.setObservacoes(taObservacoes.getText().trim());

            String msg = controller.cadastrarProfessor(p);
            JOptionPane.showMessageDialog(this, msg);
            limparCampos();
            carregarProfessores();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void atualizarProfessor() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um professor para atualizar.");
            return;
        }
        try {
            Professor p = new Professor();
            p.setId(idSelecionado);
            p.setNome(tfNome.getText().trim());
            p.setCpf(tfCpf.getText().trim());
            p.setEmail(tfEmail.getText().trim());
            p.setTelefone(tfTelefone.getText().trim());
            p.setObservacoes(taObservacoes.getText().trim());

            String msg = controller.atualizarProfessor(p);
            JOptionPane.showMessageDialog(this, msg);
            limparCampos();
            carregarProfessores();
            idSelecionado = -1;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirProfessor() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um professor para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do professor selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String msg = controller.excluirProfessor(idSelecionado);
                JOptionPane.showMessageDialog(this, msg);
                limparCampos();
                carregarProfessores();
                idSelecionado = -1;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    private void voltar() {
        dispose();
        new TelaPrincipal(usuarioLogado).setVisible(true);
    }

    private void limparCampos() {
        tfNome.setText("");
        tfCpf.setText("");
        tfEmail.setText("");
        tfTelefone.setText("");
        taObservacoes.setText("");
        tabelaProfessores.clearSelection();
        idSelecionado = -1;
    }

    private void carregarProfessores() {
        try {
            List<Professor> lista = controller.listarProfessores();
            tabelaModel.setRowCount(0);
            for (Professor p : lista) {
                tabelaModel.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone(), p.getObservacoes()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar professores: " + e.getMessage());
        }
    }

    private void carregarProfessorSelecionado() {
        int linha = tabelaProfessores.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) tabelaModel.getValueAt(linha, 0);
            tfNome.setText((String) tabelaModel.getValueAt(linha, 1));
            tfCpf.setText((String) tabelaModel.getValueAt(linha, 2));
            tfEmail.setText((String) tabelaModel.getValueAt(linha, 3));
            tfTelefone.setText((String) tabelaModel.getValueAt(linha, 4));
            taObservacoes.setText((String) tabelaModel.getValueAt(linha, 5));
        }
    }
}