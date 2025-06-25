/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
public class CargaHorariaMensal {
    private int id;
    private Professor professor;
    private int mes;
    private int ano;
    private double totalSala;
    private double totalComplementar;
    private double totalGeral;

    public CargaHorariaMensal() {}

    public CargaHorariaMensal(int id, Professor professor, int mes, int ano, double totalSala, double totalComplementar, double totalGeral) {
        this.id = id;
        this.professor = professor;
        this.mes = mes;
        this.ano = ano;
        this.totalSala = totalSala;
        this.totalComplementar = totalComplementar;
        this.totalGeral = totalGeral;
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

    public int getMes() {
        return mes; 
    }
    public void setMes(int mes) { 
        this.mes = mes; 
    }

    public int getAno() {
        return ano; 
    }
    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getTotalSala() {
        return totalSala; 
    }
    public void setTotalSala(double totalSala) {
        this.totalSala = totalSala; 
    }

    public double getTotalComplementar() { 
        return totalComplementar; 
    }
    public void setTotalComplementar(double totalComplementar) { 
        this.totalComplementar = totalComplementar; 
    }

    public double getTotalGeral() {
        return totalGeral; 
    }
    public void setTotalGeral(double totalGeral) { 
        this.totalGeral = totalGeral;
    }
}