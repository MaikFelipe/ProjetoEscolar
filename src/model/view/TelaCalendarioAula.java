/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.CalendarioAula;
import model.Disciplina;
import model.Turma;
import model.Usuario;
import model.controller.CalendarioAulaController;
import model.controller.DisciplinaController;
import model.controller.TurmaController;
import model.controller.UsuarioController;
import model.util.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaCalendarioAula extends JFrame {

    private CalendarioAulaController calendarioController;
    private TurmaController turmaController;
    private DisciplinaController disciplinaController;
    private UsuarioController usuarioController;

    private JTable tabela;
    private DefaultTableModel tabelaModel;

    private JComboBox<Turma> cbTurma;
    private JComboBox<Disciplina> cbDisciplina;
    private JComboBox<Usuario> cbProfessor;
    private JComboBox<String> cbDiaSemana;
    private JTextField tfHorarioInicio;
    private JTextField tfHorarioFim;

    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnNovo;

    private CalendarioAula selecionado = null;

    private final DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");

    public TelaCalendarioAula() {
        try {
            Connection conn = Conexao.getConexao();
            calendarioController = new CalendarioAulaController(conn);
            turmaController = new TurmaController(conn);
            disciplinaController = new DisciplinaController();
            usuarioController = new UsuarioController();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + ex.getMessage());
            System.exit(1);
        }

        setTitle("Calendário de Aulas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        carregarComboBoxes();
        carregarTabela();

        setVisible(true);
    }

    private void initComponents() {
        // Painel de formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0 - Turma
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        cbTurma = new JComboBox<>();
        painelForm.add(cbTurma, gbc);

        // Linha 1 - Disciplina
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        cbDisciplina = new JComboBox<>();
        painelForm.add(cbDisciplina, gbc);

        // Linha 2 - Professor
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Professor:"), gbc);
        gbc.gridx = 1;
        cbProfessor = new JComboBox<>();
        painelForm.add(cbProfessor, gbc);

        // Linha 3 - Dia da Semana
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Dia da Semana:"), gbc);
        gbc.gridx = 1;
        cbDiaSemana = new JComboBox<>(new String[]{
                "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"
        });
        painelForm.add(cbDiaSemana, gbc);

        // Linha 4 - Horário Início
        gbc.gridx = 0; gbc.gridy = 4;
        painelForm.add(new JLabel("Horário Início (HH:mm):"), gbc);
        gbc.gridx = 1;
        tfHorarioInicio = new JTextField();
        painelForm.add(tfHorarioInicio, gbc);

        // Linha 5 - Horário Fim
        gbc.gridx = 0; gbc.gridy = 5;
        painelForm.add(new JLabel("Horário Fim (HH:mm):"), gbc);
        gbc.gridx = 1;
        tfHorarioFim = new JTextField();
        painelForm.add(tfHorarioFim, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnNovo = new JButton("Novo");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnNovo);

        btnExcluir.setEnabled(false);

        // Tabela
        tabelaModel = new DefaultTableModel(new Object[]{
                "ID", "Turma", "Disciplina", "Professor", "Dia Semana", "Início", "Fim"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(tabelaModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Layout geral
        Container c = getContentPane();
        c.setLayout(new BorderLayout(10,10));
        c.add(new JScrollPane(tabela), BorderLayout.CENTER);
        c.add(painelForm, BorderLayout.WEST);
        c.add(painelBotoes, BorderLayout.SOUTH);

        // Listeners
        btnSalvar.addActionListener(e -> salvar());
        btnExcluir.addActionListener(e -> excluir());
        btnNovo.addActionListener(e -> limparFormulario());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarSelecionado();
        });
    }

    private void carregarComboBoxes() {
        try {
            cbTurma.removeAllItems();
            List<Turma> turmas = turmaController.listarTurmas();
            if(turmas != null){
                for (Turma t : turmas) cbTurma.addItem(t);
            }

            cbDisciplina.removeAllItems();
            List<Disciplina> disciplinas = disciplinaController.listarTodos();
            if(disciplinas != null){
                for (Disciplina d : disciplinas) cbDisciplina.addItem(d);
            }

            cbProfessor.removeAllItems();
            List<Usuario> professores = usuarioController.listarPorNivelAcesso(5); // nível 5 = professores
            if(professores != null){
                for (Usuario u : professores) cbProfessor.addItem(u);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combos: " + ex.getMessage());
        }
    }

    private void carregarTabela() {
        try {
            tabelaModel.setRowCount(0);
            List<CalendarioAula> lista = calendarioController.listarTodos();
            if(lista != null){
                for (CalendarioAula c : lista) {
                    tabelaModel.addRow(new Object[]{
                            c.getId(),
                            c.getTurma() != null ? c.getTurma().getNome() : "",
                            c.getDisciplina() != null ? c.getDisciplina().getNome() : "",
                            c.getProfessor() != null ? c.getProfessor().getNomeCompleto() : "",
                            c.getDiaSemana(),
                            c.getHorarioInicio() != null ? c.getHorarioInicio().format(fmtHora) : "",
                            c.getHorarioFim() != null ? c.getHorarioFim().format(fmtHora) : ""
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage());
        }
    }

    private void carregarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            try {
                int id = (int) tabelaModel.getValueAt(linha, 0);
                selecionado = calendarioController.buscar(id);
                if (selecionado != null) {
                    cbTurma.setSelectedItem(selecionado.getTurma());
                    cbDisciplina.setSelectedItem(selecionado.getDisciplina());
                    cbProfessor.setSelectedItem(selecionado.getProfessor());
                    cbDiaSemana.setSelectedItem(selecionado.getDiaSemana());
                    tfHorarioInicio.setText(selecionado.getHorarioInicio().format(fmtHora));
                    tfHorarioFim.setText(selecionado.getHorarioFim().format(fmtHora));
                    btnExcluir.setEnabled(true);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar item selecionado: " + ex.getMessage());
            }
        }
    }

    private void limparFormulario() {
        selecionado = null;
        tabela.clearSelection();
        cbTurma.setSelectedIndex(-1);
        cbDisciplina.setSelectedIndex(-1);
        cbProfessor.setSelectedIndex(-1);
        cbDiaSemana.setSelectedIndex(0);
        tfHorarioInicio.setText("");
        tfHorarioFim.setText("");
        btnExcluir.setEnabled(false);
    }

    private void salvar() {
        try {
            if (cbTurma.getSelectedItem() == null) throw new IllegalArgumentException("Selecione uma turma.");
            if (cbDisciplina.getSelectedItem() == null) throw new IllegalArgumentException("Selecione uma disciplina.");
            if (cbProfessor.getSelectedItem() == null) throw new IllegalArgumentException("Selecione um professor.");

            Turma turma = (Turma) cbTurma.getSelectedItem();
            Disciplina disciplina = (Disciplina) cbDisciplina.getSelectedItem();
            Usuario professor = (Usuario) cbProfessor.getSelectedItem();
            String diaSemana = (String) cbDiaSemana.getSelectedItem();

            LocalTime horarioInicio = LocalTime.parse(tfHorarioInicio.getText().trim(), fmtHora);
            LocalTime horarioFim = LocalTime.parse(tfHorarioFim.getText().trim(), fmtHora);

            if (horarioFim.isBefore(horarioInicio)) throw new IllegalArgumentException("Horário fim deve ser depois do início.");

            if (selecionado == null) selecionado = new CalendarioAula();

            selecionado.setTurma(turma);
            selecionado.setDisciplina(disciplina);
            selecionado.setProfessor(professor);
            selecionado.setDiaSemana(diaSemana);
            selecionado.setHorarioInicio(horarioInicio);
            selecionado.setHorarioFim(horarioFim);

            calendarioController.salvar(selecionado);
            JOptionPane.showMessageDialog(this, "Salvo com sucesso.");
            carregarTabela();
            limparFormulario();
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (selecionado == null) return;
        int resp = JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                calendarioController.excluir(selecionado.getId());
                JOptionPane.showMessageDialog(this, "Excluído com sucesso.");
                carregarTabela();
                limparFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    // Para que os JComboBox exibam nomes e não objetos.toString padrão
    private static class ComboRenderer<T> extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
            super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            if (value != null) {
                if (value instanceof Turma) {
                    setText(((Turma)value).getNome());
                } else if (value instanceof Disciplina) {
                    setText(((Disciplina)value).getNome());
                } else if (value instanceof Usuario) {
                    setText(((Usuario)value).getNomeCompleto());
                }
            } else {
                setText("");
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCalendarioAula());
    }
}