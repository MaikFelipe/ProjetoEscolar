/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.util;

/**
 *
 * @author LASEDi 1781
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gestao_escola";
        String usuario = "root";
        String senha = "";
        return DriverManager.getConnection(url, usuario, senha);
    }
}