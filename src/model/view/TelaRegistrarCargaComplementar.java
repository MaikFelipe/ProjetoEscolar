/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.CargaHorariaMensal;
import model.Professor;
import model.controller.CargaHorariaMensalController;
import model.dao.ProfessorDAO;
import model.util.Conexao;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaRegistrarCargaComplementar extends JFrame {

    private JComboBox<Professor> cbProfessores;
    private JTextField tfMes, tfAno, tfHoras, tfJustificativa;
    private JButton btnSalvar, btnVoltar;

    public TelaRegistrarCargaComplementar() {
        setTitle("Registrar Carga Horária Complementar");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cbProfessores = new JComboBox<>();
        tfMes = new JTextField();
        tfAno = new JTextField();
        tfHoras = new JTextField();
        tfJustificativa = new JTextField();

        painelForm.add(new JLabel("Professor:"));
        painelForm.add(cbProfessores);

        painelForm.add(new JLabel("Mês (1-12):"));
        painelForm.add(tfMes);

        painelForm.add(new JLabel("Ano:"));
        painelForm.add(tfAno);

        painelForm.add(new JLabel("Horas Complementares:"));
        painelForm.add(tfHoras);

        painelForm.add(new JLabel("Justificativa:"));
        painelForm.add(tfJustificativa);

        btnSalvar = new JButton("Salvar");
        btnVoltar = new JButton("Voltar");

        painelForm.add(btnSalvar);
        painelForm.add(btnVoltar);

        add(painelForm, BorderLayout.CENTER);

        carregarProfessores();

        btnSalvar.addActionListener(e -> salvarCargaComplementar());
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

    private void salvarCargaComplementar() {
        Professor professor = (Professor) cbProfessores.getSelectedItem();
        if (professor == null) return;

        try {
            int mes = Integer.parseInt(tfMes.getText());
            int ano = Integer.parseInt(tfAno.getText());
            double horas = Double.parseDouble(tfHoras.getText());
            String justificativa = tfJustificativa.getText();

            if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mês inválido.");

            CargaHorariaMensal carga = new CargaHorariaMensal();
            carga.setProfessor(professor);
            carga.setMes(mes);
            carga.setAno(ano);
            carga.setTotalComplementar(horas);
            carga.setTotalSala(0);
            carga.setTotalGeral(horas);

            try (Connection conn = Conexao.getConexao()) {
                CargaHorariaMensalController controller = new CargaHorariaMensalController(conn);
                controller.salvar(carga);
                JOptionPane.showMessageDialog(this, "Carga complementar registrada com sucesso.");
                dispose();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Campos de mês, ano e horas devem ser numéricos.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}