package com.fpdual.javaweb.util;

import java.security.MessageDigest;

/**
 * Clase de utiles comunes.
 */
public class Utils {

    /**
     * Encripta una contraseña utilizando el algoritmo de hash MD5.
     *
     * @param password la contraseña a encriptar.
     * @return la contraseña encriptada como una cadena de caracteres hexadecimal.
     *         Si ocurre alguna excepción durante el proceso de encriptación, retorna null.
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] digest = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}