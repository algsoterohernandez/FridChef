package com.fpdual.javaweb.web.servlet.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.security.MessageDigest;

@Data

public class UserDto {
    private int id;
    private String name, surname1, surname2, email, password;

    private boolean alreadyExists;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private MessageDigest md5;



    public UserDto() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void encryptPassword() {
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        this.password = sb.toString();
    }

}