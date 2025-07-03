/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.view;

/**
 *
 * @author LASEDi 1781
 */
import model.Usuario;
import model.Professor;
import model.Disciplina;
import model.controller.UsuarioController;
import model.controller.ProfessorController;
import model.controller.DisciplinaController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroProfessor extends JFrame {

    private JTextField tfNome, tfCpf, tfEmail, tfTelefone, tfCargo, tfLogin;
    private JPasswordField pfSenha;
    private JComboBox<Disciplina> cbDisciplinas;
    private JTextArea taObservacoes;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private List<Disciplina> listaDisciplinas;
    private Professor professorEditando;

    public TelaCadastroProfessor(JFrame parent, Professor professor) {
        setTitle(professor == null ? "Cadastro de Professor" : "Editar Professor");
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadDisciplinas();

        this.professorEditando = professor;
        if (professor != null) {
            carregarCampos(professor);
        }
    }

    private void initComponents() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfNome = new JTextField(25);
        tfCpf = new JTextField(15);
        tfEmail = new JTextField(25);
        tfTelefone = new JTextField(15);
        tfCargo = new JTextField(15);
        tfLogin = new JTextField(15);
        pfSenha = new JPasswordField(15);
        taObservacoes = new JTextArea(5, 25);
        taObservacoes.setLineWrap(true);
        taObservacoes.setWrapStyleWord(true);
        JScrollPane spObs = new JScrollPane(taObservacoes);

        cbDisciplinas = new JComboBox<>();

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnCancelar = new JButton("Cancelar");

        int y = 0;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Nome Completo:"), c);
        c.gridx = 1; painel.add(tfNome, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("CPF:"), c);
        c.gridx = 1; painel.add(tfCpf, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Email:"), c);
        c.gridx = 1; painel.add(tfEmail, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Telefone:"), c);
        c.gridx = 1; painel.add(tfTelefone, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Cargo:"), c);
        c.gridx = 1; painel.add(tfCargo, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Login:"), c);
        c.gridx = 1; painel.add(tfLogin, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Senha:"), c);
        c.gridx = 1; painel.add(pfSenha, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Disciplina:"), c);
        c.gridx = 1; painel.add(cbDisciplinas, c); y++;

        c.gridx = 0; c.gridy = y; painel.add(new JLabel("Observações:"), c);
        c.gridx = 1; painel.add(spObs, c); y++;

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        c.gridx = 0; c.gridy = y; c.gridwidth = 2;
        painel.add(painelBotoes, c);

        add(painel);

        btnSalvar.addActionListener(e -> salvarProfessor());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void loadDisciplinas() {
        try {
            DisciplinaController dc = new DisciplinaController();
            listaDisciplinas = dc.listarTodos();
            DefaultComboBoxModel<Disciplina> model = new DefaultComboBoxModel<>();
            for (Disciplina d : listaDisciplinas) {
                model.addElement(d);
            }
            cbDisciplinas.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    private void carregarCampos(Professor p) {
        Usuario u = p.getUsuario();
        tfNome.setText(u.getNomeCompleto());
        tfCpf.setText(u.getCpf());
        tfEmail.setText(u.getEmail());
        tfTelefone.setText(u.getTelefone());
        tfCargo.setText(u.getCargo());
        tfLogin.setText(u.getLogin());
        pfSenha.setText("");
        taObservacoes.setText(p.getObservacoes());
        if (p.getDisciplina() != null) {
            cbDisciplinas.setSelectedItem(p.getDisciplina());
        }
    }

    private void salvarProfessor() {
        try {
            if (tfNome.getText().trim().isEmpty() || tfLogin.getText().trim().isEmpty() || (professorEditando == null && pfSenha.getPassword().length == 0)) {
                JOptionPane.showMessageDialog(this, "Preencha nome, login e senha.");
                return;
            }

            Usuario usuario = professorEditando != null ? professorEditando.getUsuario() : new Usuario();

            usuario.setNomeCompleto(tfNome.getText().trim());
            usuario.setCpf(tfCpf.getText().trim());
            usuario.setEmail(tfEmail.getText().trim());
            usuario.setTelefone(tfTelefone.getText().trim());
            usuario.setCargo(tfCargo.getText().trim());
            usuario.setLogin(tfLogin.getText().trim());

            if (pfSenha.getPassword().length > 0) {
                usuario.setSenha(new String(pfSenha.getPassword())); // sem criptografia
            } else if (professorEditando == null) {
                JOptionPane.showMessageDialog(this, "Senha é obrigatória para novo cadastro.");
                return;
            }

            usuario.setNivelAcesso(5); // Professor

            Professor professor = professorEditando != null ? professorEditando : new Professor();
            professor.setUsuario(usuario);
            professor.setDisciplina((Disciplina) cbDisciplinas.getSelectedItem());
            professor.setObservacoes(taObservacoes.getText().trim());

            ProfessorController pc = new ProfessorController();

            if (professorEditando == null) {
                pc.cadastrarProfessor(professor);
                JOptionPane.showMessageDialog(this, "Professor cadastrado com sucesso!");
            } else {
                pc.atualizarProfessor(professor);
                JOptionPane.showMessageDialog(this, "Professor atualizado com sucesso!");
            }

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        tfNome.setText("");
        tfCpf.setText("");
        tfEmail.setText("");
        tfTelefone.setText("");
        tfCargo.setText("");
        tfLogin.setText("");
        pfSenha.setText("");
        taObservacoes.setText("");
        if (cbDisciplinas.getItemCount() > 0) {
            cbDisciplinas.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaCadastroProfessor(null, null).setVisible(true);
        });
    }
}