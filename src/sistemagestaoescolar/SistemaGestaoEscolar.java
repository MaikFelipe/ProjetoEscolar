/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemagestaoescolar;

import java.sql.SQLException;
import model.Municipio;
import model.Usuario;
import model.controller.UsuarioController;
import model.dao.UsuarioDao;
import model.util.Conexao;
import model.util.Criptografia;
import java.sql.SQLException;

/**
 *
 * @author LASEDi 1781
 */
public class SistemaGestaoEscolar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
    UsuarioController uc = new UsuarioController();
    String login = "usuarioNivel4"; // login de um usuário nível 4 que você conhece
    String senhaTexto = "senha123"; // senha que esse usuário usa
    String senhaCriptografada = Criptografia.criptografar(senhaTexto);
    Usuario u = uc.autenticar(login, senhaCriptografada);
    if (u != null) {
        System.out.println("Usuário autenticado: " + u.getNomeCompleto() + ", Nível: " + u.getNivelAcesso());
    } else {
        System.out.println("Falha na autenticação");
    }
}

}