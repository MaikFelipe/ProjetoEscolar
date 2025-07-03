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
import model.CargaHorariaMensal;
import model.controller.CargaHorariaMensalController;
import model.dao.ProfessorDAO;
import model.util.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaRelatorioCargaHoraria extends JFrame {

    private JComboBox<Professor> cbProfessores;
    private JButton btnBuscar, btnVoltar;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public TelaRelatorioCargaHoraria() {
        setTitle("Relatório de Carga Horária por Professor");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout());
        cbProfessores = new JComboBox<>();
        btnBuscar = new JButton("Buscar");
        btnVoltar = new JButton("Voltar");

        painelTopo.add(new JLabel("Professor:"));
        painelTopo.add(cbProfessores);
        painelTopo.add(btnBuscar);
        painelTopo.add(btnVoltar);
        add(painelTopo, BorderLayout.NORTH);

        String[] colunas = {"Mês", "Ano", "Total Sala", "Total Complementar", "Total Geral"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarProfessores();

        btnBuscar.addActionListener(e -> buscarCargaHoraria());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarProfessores() {
        try (Connection conn = Conexao.getConexao()) {
            ProfessorDAO professorDAO = new ProfessorDAO(conn);
            List<Professor> lista = professorDAO.listarTodos();
            for (Professor p : lista) {
                cbProfessores.addItem(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar professores: " + ex.getMessage());
        }
    }

    private void buscarCargaHoraria() {
        tableModel.setRowCount(0);
        Professor professor = (Professor) cbProfessores.getSelectedItem();
        if (professor == null) return;

        try (Connection conn = Conexao.getConexao()) {
            CargaHorariaMensalController controller = new CargaHorariaMensalController(conn);
            List<CargaHorariaMensal> lista = controller.listarTodos();
            for (CargaHorariaMensal c : lista) {
                if (c.getProfessor().getId() == professor.getId()) {
                    Object[] linha = {
                        String.format("%02d", c.getMes()),
                        c.getAno(),
                        c.getTotalSala(),
                        c.getTotalComplementar(),
                        c.getTotalGeral()
                    };
                    tableModel.addRow(linha);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar carga horária: " + ex.getMessage());
        }
    }
}