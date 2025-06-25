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
import model.Aluno;
import model.Turma;

public class Frequencia {
    private int id;
    private Aluno aluno;
    private Turma turma;
    private LocalDate data;
    private boolean presente;
    private int totalFaltasAcumuladas;

    public Frequencia() {}

    public Frequencia(int id, Aluno aluno, Turma turma, LocalDate data, boolean presente, int totalFaltasAcumuladas) {
        this.id = id;
        this.aluno = aluno;
        this.turma = turma;
        this.data = data;
        this.presente = presente;
        this.totalFaltasAcumuladas = totalFaltasAcumuladas;
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

    public LocalDate getData() { 
        return data; 
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPresente() {
        return presente; 
    }
    public void setPresente(boolean presente) { 
        this.presente = presente; 
    }

    public int getTotalFaltasAcumuladas() { 
        return totalFaltasAcumuladas; 
    }
    public void setTotalFaltasAcumuladas(int totalFaltasAcumuladas) { 
        this.totalFaltasAcumuladas = totalFaltasAcumuladas;
    }
}