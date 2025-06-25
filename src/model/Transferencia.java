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
import model.Escola;

public class Transferencia {
    private int id;
    private Aluno aluno;
    private Escola escolaOrigem;
    private Escola escolaDestino;
    private LocalDate dataSolicitacao;
    private String status;
    private boolean notasMigradas;

    public Transferencia() {}

    public Transferencia(int id, Aluno aluno, Escola escolaOrigem, Escola escolaDestino, LocalDate dataSolicitacao, String status, boolean notasMigradas) {
        this.id = id;
        this.aluno = aluno;
        this.escolaOrigem = escolaOrigem;
        this.escolaDestino = escolaDestino;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.notasMigradas = notasMigradas;
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

    public Escola getEscolaOrigem() { 
        return escolaOrigem;
    }
    public void setEscolaOrigem(Escola escolaOrigem) { 
        this.escolaOrigem = escolaOrigem;
    }

    public Escola getEscolaDestino() { 
        return escolaDestino; 
    }
    public void setEscolaDestino(Escola escolaDestino) { 
        this.escolaDestino = escolaDestino;
    }

    public LocalDate getDataSolicitacao() { 
        return dataSolicitacao;
    }
    public void setDataSolicitacao(LocalDate dataSolicitacao) { 
        this.dataSolicitacao = dataSolicitacao; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) {
        this.status = status; 
    }

    public boolean isNotasMigradas() {
        return notasMigradas; 
    }
    public void setNotasMigradas(boolean notasMigradas) {
        this.notasMigradas = notasMigradas;
    }
}