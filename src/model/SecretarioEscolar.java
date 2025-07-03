/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LASEDi 1781
 */
public class SecretarioEscolar {
    private int id; // novo atributo
    private Usuario usuario;
    private Escola escola;

    public SecretarioEscolar() {}

    public SecretarioEscolar(int id, Usuario usuario, Escola escola) {
        this.id = id;
        this.usuario = usuario;
        this.escola = escola;
    }

    public SecretarioEscolar(Usuario usuario, Escola escola) {
        this.usuario = usuario;
        this.escola = escola;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }
}
