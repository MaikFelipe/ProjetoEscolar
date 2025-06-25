/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
import java.time.LocalDate;

public class FaltaProfessor {
    private int id;
    private Professor professor;
    private Disciplina disciplina;
    private LocalDate data;
    private String motivo;
    private String documentoAnexo;
    private Usuario usuarioRegistro;

    public FaltaProfessor() {}

    public FaltaProfessor(int id, Professor professor, Disciplina disciplina, LocalDate data, String motivo, String documentoAnexo, Usuario usuarioRegistrou) {
        this.id = id;
        this.professor = professor;
        this.disciplina = disciplina;
        this.data = data;
        this.motivo = motivo;
        this.documentoAnexo = documentoAnexo;
        this.usuarioRegistro = usuarioRegistrou;
    }

    public int getId() {
        return id; 
    }
    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() { 
        return professor;
    }
    public void setProfessor(Professor professor) { 
        this.professor = professor; 
    }

    public Disciplina getDisciplina() { 
        return disciplina; 
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina; 
    }

    public LocalDate getData() { 
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data; 
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDocumentoAnexo() {
        return documentoAnexo; 
    }
    public void setDocumentoAnexo(String documentoAnexo) { this.documentoAnexo = documentoAnexo; }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }
    public void setUsuarioRegistrou(Usuario usuarioRegistrou) { 
        this.usuarioRegistro = usuarioRegistrou; 
    }
}