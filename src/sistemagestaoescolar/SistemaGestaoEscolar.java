/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemagestaoescolar;

/**
 *
 * @author LASEDi 1781
 */
import model.Municipio;
import model.Usuario;
import model.controller.MunicipioController;
import model.dao.UsuarioDao;
import model.util.Conexao;
import java.sql.Connection;
import java.util.Scanner;

public class SistemaGestaoEscolar {

    public static void main(String[] args) {
        try (Connection connection = Conexao.getConexao()) {

            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario secretario = usuarioDao.buscarPorLogin("alon");

            if (secretario == null) {
                System.out.println("Erro: Secretário 'alon' não encontrado no banco de dados.");
                return;
            }

            if (secretario.getNivelAcesso() != 1) {
                System.out.println("Erro: Usuário 'alon' não tem nível de acesso 1 (Secretário de Educação).");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nome do Município: ");
            String nome = scanner.nextLine();

            System.out.print("Estado: ");
            String estado = scanner.nextLine();

            Municipio municipio = new Municipio();
            municipio.setNome(nome);
            municipio.setEstado(estado);
            municipio.setSecretarioEducacao(secretario);

            MunicipioController controller = new MunicipioController(connection);
            controller.salvar(municipio);

            System.out.println("Município cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
