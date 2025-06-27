/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemagestaoescolar;

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
        Usuario u = new Usuario();
        u.setNomeCompleto("Administrador");
        u.setCpf("00000000000");
        u.setEmail("admin@escola.com");
        u.setTelefone("000000000");
        u.setCargo("Administrador");
        u.setLogin("admin");
        u.setSenha("1234");
        u.setNivelAcesso("admin");

        UsuarioController controller = new UsuarioController();
        String resultado = controller.cadastrarUsuario(u);
        System.out.println(resultado);
    }
}
