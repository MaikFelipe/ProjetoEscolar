/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
public class Municipio {
    private int id;
    private String nome;
    private String estado;
    private Usuario secretarioEducacao;
    
    public Municipio() {}
    
    public Municipio(int id, String nome, String estado, Usuario secretarioEducacao) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.secretarioEducacao = secretarioEducacao;
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome(){
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Usuario getSecretarioEducacao(){
        return secretarioEducacao;
    }
    public void setSecretarioEducacao(Usuario secretarioEducacao) {
        this.secretarioEducacao = secretarioEducacao;
    }
}