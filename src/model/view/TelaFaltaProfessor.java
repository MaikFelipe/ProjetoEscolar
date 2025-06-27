/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Professor;
import model.controller.ProfessorController;
import model.Usuario;

public class TelaFaltaProfessor extends JFrame {

    private JTable tabelaFaltas;
    private JButton btnVoltar;
    private ProfessorController controller;
    private Usuario usuarioLogado;

    public TelaFaltaProfessor(Usuario usuario) {
        this.usuarioLogado = usuario;
        controller = new ProfessorController();
        setTitle("Faltas dos Professores");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        tabelaFaltas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaFaltas);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        List<Professor> lista = controller.listarProfessores();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("Telefone");
        modelo.addColumn("Faltas (Carga Horária Complementar)");

        for (Professor p : lista) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getEmail(),
                p.getTelefone(),
                p.getCargaHorariaComplementar() + " horas"
            });
        }

        tabelaFaltas.setModel(modelo);
    }
}