/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.util;

/**
 *
 * @author LASEDi 1781
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    /**
     * Gera o hash SHA-256 da senha informada.
     *
     * @param senha A senha em texto puro.
     * @return String com o hash hexadecimal da senha.
     */
    public static String criptografar(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException ex) {
            throw new RuntimeException("Erro ao criptografar senha: " + ex.getMessage(), ex);
        }
    }
    
}