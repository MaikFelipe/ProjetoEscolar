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
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import model.*;
import model.controller.TransferenciaController;
import model.dao.AlunoDAO;
import model.dao.EscolaDAO;

public class TelaTransferenciaAluno extends JFrame {

    private JComboBox<Aluno> comboAluno;
    private JComboBox<Escola> comboOrigem;
    private JComboBox<Escola> comboDestino;
    private JComboBox<String> comboStatus;
    private JCheckBox chkNotasMigradas;
    private JButton btnSalvar, btnVoltar;

    private TransferenciaController controller;
    private Usuario usuarioLogado;

    public TelaTransferenciaAluno(Usuario usuario) {
        this.usuarioLogado = usuario;
        controller = new TransferenciaController();
        setTitle("Transferência de Aluno");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboAluno = new JComboBox<>();
        comboOrigem = new JComboBox<>();
        comboDestino = new JComboBox<>();
        comboStatus = new JComboBox<>(new String[]{"Solicitada", "Aprovada", "Recusada"});
        chkNotasMigradas = new JCheckBox("Notas migradas");
        btnSalvar = new JButton("Salvar Transferência");
        btnVoltar = new JButton("Voltar");

        int y = 0;
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        painel.add(comboAluno, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(new JLabel("Escola de Origem:"), gbc);
        gbc.gridx = 1;
        painel.add(comboOrigem, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(new JLabel("Escola de Destino:"), gbc);
        gbc.gridx = 1;
        painel.add(comboDestino, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        painel.add(comboStatus, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        painel.add(chkNotasMigradas, gbc);

        y++;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        painel.add(btnSalvar, gbc);

        gbc.gridx = 1;
        painel.add(btnVoltar, gbc);

        btnSalvar.addActionListener(e -> salvarTransferencia());
        btnVoltar.addActionListener(e -> dispose());

        add(painel);
    }

    private void carregarDados() {
        try {
            AlunoDAO alunoDAO = new AlunoDAO();
            EscolaDAO escolaDAO = new EscolaDAO();

            List<Aluno> alunos = alunoDAO.listarTodos();
            comboAluno.removeAllItems();
            for (Aluno a : alunos) comboAluno.addItem(a);

            List<Escola> escolas = escolaDAO.listarTodas();
            comboOrigem.removeAllItems();
            comboDestino.removeAllItems();
            for (Escola e : escolas) {
                comboOrigem.addItem(e);
                comboDestino.addItem(e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void salvarTransferencia() {
        try {
            Aluno aluno = (Aluno) comboAluno.getSelectedItem();
            Escola origem = (Escola) comboOrigem.getSelectedItem();
            Escola destino = (Escola) comboDestino.getSelectedItem();
            String status = (String) comboStatus.getSelectedItem();
            boolean notasMigradas = chkNotasMigradas.isSelected();

            if (aluno == null || origem == null || destino == null) {
                JOptionPane.showMessageDialog(this, "Selecione todas as opções.");
                return;
            }

            if (origem.getId() == destino.getId()) {
                JOptionPane.showMessageDialog(this, "A escola de origem e destino devem ser diferentes.");
                return;
            }

            Transferencia transferencia = new Transferencia();
            transferencia.setAluno(aluno);
            transferencia.setEscolaOrigem(origem);
            transferencia.setEscolaDestino(destino);
            transferencia.setStatus(status);
            transferencia.setNotasMigradas(notasMigradas);
            transferencia.setDataSolicitacao(LocalDate.now());

            String msg = controller.cadastrarTransferencia(transferencia);
            JOptionPane.showMessageDialog(this, msg);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar transferência: " + e.getMessage());
        }
    }
}
