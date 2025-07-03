
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
public class Turma {
    private int id;
    private String nome;
    private String serie;
    private String nivelEnsino;
    private int anoLetivo;
    private String turno;
    private int numeroMinimoAlunos;
    private int numeroMaximoAlunos;
    private Escola escola;
    private Professor professorResponsavel;

    public Turma() {}

    public Turma(int id, String nome, String serie, String nivelEnsino, int anoLetivo, String turno,
                 int numeroMinimoAlunos, int numeroMaximoAlunos, Escola escola, Professor professorResponsavel) {
        this.id = id;
        this.nome = nome;
        this.serie = serie;
        this.nivelEnsino = nivelEnsino;
        this.anoLetivo = anoLetivo;
        this.turno = turno;
        this.numeroMinimoAlunos = numeroMinimoAlunos;
        this.numeroMaximoAlunos = numeroMaximoAlunos;
        this.escola = escola;
        this.professorResponsavel = professorResponsavel;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getNivelEnsino() { return nivelEnsino; }
    public void setNivelEnsino(String nivelEnsino) { this.nivelEnsino = nivelEnsino; }

    public int getAnoLetivo() { return anoLetivo; }
    public void setAnoLetivo(int anoLetivo) { this.anoLetivo = anoLetivo; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public int getNumeroMinimoAlunos() { return numeroMinimoAlunos; }
    public void setNumeroMinimoAlunos(int numeroMinimoAlunos) { this.numeroMinimoAlunos = numeroMinimoAlunos; }

    public int getNumeroMaximoAlunos() { return numeroMaximoAlunos; }
    public void setNumeroMaximoAlunos(int numeroMaximoAlunos) { this.numeroMaximoAlunos = numeroMaximoAlunos; }

    public Escola getEscola() { return escola; }
    public void setEscola(Escola escola) { this.escola = escola; }

    public Professor getProfessorResponsavel() { return professorResponsavel; }
    public void setProfessorResponsavel(Professor professorResponsavel) { this.professorResponsavel = professorResponsavel; }

    @Override
    public String toString() {
        return nome;
    }
}
