/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
public class Disciplina {
    private int id;
    private String nome;

    public Disciplina() {}

    public Disciplina(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id; 
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) { 
        this.nome = nome;
    }
}