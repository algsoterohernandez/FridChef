package com.fpdual.javaweb.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;

public class Utils {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private MessageDigest md5;

    public String encryptPassword(String password) {
        try {
            md5 = MessageDigest.getInstance("MD5");
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