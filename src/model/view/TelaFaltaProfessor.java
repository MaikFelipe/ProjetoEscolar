/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import java.time.format.DateTimeFormatter;
import model.FaltaProfessor;
import model.controller.FaltaProfessorController;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Professor;

public class TelaFaltaProfessor extends JFrame {

    private JTable tabela;
    private DefaultTableModel tableModel;
    private JButton btnVoltar;

    public TelaFaltaProfessor() {
        setTitle("Faltas de Professores");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"Professor", "Data da Falta", "Motivo", "Observações"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);

        btnVoltar = new JButton("Voltar");
        JPanel painelInferior = new JPanel();
        painelInferior.add(btnVoltar);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        carregarFaltas();

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarFaltas() {
        try (Connection conn = Conexao.getConexao()) {
            FaltaProfessorController controller = new FaltaProfessorController(conn);
            List<FaltaProfessor> faltas = controller.listarTodos();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (FaltaProfessor f : faltas) {
                Professor professor = f.getProfessor();
                String nome = professor != null && professor.getUsuario() != null
                        ? professor.getUsuario().getNomeCompleto() : "Desconhecido";

                String dataFormatada = f.getData() != null ? f.getData().format(formatter) : "";
                String motivo = f.getMotivo() != null ? f.getMotivo() : "";
                String observacoes = f.getDocumentoAnexo() != null ? f.getDocumentoAnexo() : "";

                Object[] linha = {nome, dataFormatada, motivo, observacoes};
                tableModel.addRow(linha);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar faltas: " + ex.getMessage());
        }
    }
}