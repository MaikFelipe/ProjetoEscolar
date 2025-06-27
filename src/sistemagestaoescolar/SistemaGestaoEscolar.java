/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemagestaoescolar;

import model.Municipio;
import model.Usuario;
import model.controller.UsuarioController;

/**
 *
 * @author LASEDi 1781
 */
public class SistemaGestaoEscolar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Municipio municipio = new Municipio();
        municipio.setId(1);
        municipio.setNome("Santa Luzia");
        municipio.setEstado("MG");
        municipio.setSecretarioEducacao("Maria da Silva");

        System.out.println("ID: " + municipio.getId());
        System.out.println("Nome: " + municipio.getNome());
        System.out.println("Estado: " + municipio.getEstado());
        System.out.println("Secretário de Educação: " + municipio.getSecretarioEducacao());
    }
}