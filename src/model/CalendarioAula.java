package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LASEDi 1781
 */
import java.time.LocalTime;

public class CalendarioAula {
    private int id;
    private Turma turma;
    private Disciplina disciplina;
    private Usuario professor;
    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public CalendarioAula() {}

    public CalendarioAula(int id, Turma turma, Disciplina disciplina, Usuario professor, String diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.id = id;
        this.turma = turma;
        this.disciplina = disciplina;
        this.professor = professor;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) { 
        this.id = id;
    }

    public Turma getTurma() {
        return turma; 
    }
    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Disciplina getDisciplina() {
        return disciplina; 
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Usuario getProfessor() { 
        return professor;
    }
    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) { 
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioInicio() { 
        return horarioInicio;
    }
    public void setHorarioInicio(LocalTime horarioInicio) { 
        this.horarioInicio = horarioInicio; 
    }

    public LocalTime getHorarioFim() {
        return horarioFim; 
    }
    public void setHorarioFim(LocalTime horarioFim) { 
        this.horarioFim = horarioFim;
    }
}