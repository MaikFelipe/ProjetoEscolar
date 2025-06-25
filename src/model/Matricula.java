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
import java.util.List;
import model.Aluno;
import model.Nota;
import model.Turma;
public class Matricula {
    private int id;
    private Aluno aluno;
    private Turma turma;
    private LocalDate dataMatricula;
    private String status;
    private List<Nota> historicoNotas;
    
    
    public Matricula() {}
    
    public Matricula(int id, Aluno aluno, Turma turma, LocalDate dataMatricula, String status, List<Nota> historicoNotas) {
        this.id = id;
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
        this.status = status;
        this.historicoNotas = historicoNotas;
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    public Turma getTurma() {
        return turma;
    }
    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula =dataMatricula;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Nota> getHistoricoNotas() {
        return historicoNotas;
    }
    public void setHistoricoNotas(List<Nota> historicoNotas) {
        this.historicoNotas = historicoNotas;
    }
}
