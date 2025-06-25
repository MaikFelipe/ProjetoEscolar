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

public class Aluno {
    private int id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private String enderecoCompleto;
    private String telefone;
    private String nomeResponsavel;
    private String cpfResponsavel;
    private String emailResponsavel;
    private String telefoneResponsavel;

    public Aluno() {}

    public Aluno(int id, String nomeCompleto, LocalDate dataNascimento, String cpf, String enderecoCompleto, String telefone, String nomeResponsavel, String cpfResponsavel, String emailResponsavel, String telefoneResponsavel) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.enderecoCompleto = enderecoCompleto;
        this.telefone = telefone;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfResponsavel = cpfResponsavel;
        this.emailResponsavel = emailResponsavel;
        this.telefoneResponsavel = telefoneResponsavel;
    }


    public int getId() { 
        return id;
    }
    public void setId(int id) { 
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto; 
    }
    public void setNomeCompleto(String nomeCompleto) { 
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() { 
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento; 
    }

    public String getCpf() { 
        return cpf; 
    }
    public void setCpf(String cpf) {
        this.cpf = cpf; 
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto; 
    }
    public void setEnderecoCompleto(String enderecoCompleto) { 
        this.enderecoCompleto = enderecoCompleto;
    }

    public String getTelefone() { 
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone; 
    }

    public String getNomeResponsavel() { 
        return nomeResponsavel; 
    }
    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }
    public void setCpfResponsavel(String cpfResponsavel) { 
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getEmailResponsavel() { 
        return emailResponsavel;
    }
    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel; 
    }
    
    public String getTelefoneResponsavel(){
        return telefoneResponsavel;
    }
    public void setTelefoneresponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }
}