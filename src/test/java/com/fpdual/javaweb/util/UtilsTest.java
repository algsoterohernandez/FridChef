package com.fpdual.javaweb.util;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class UtilsTest {

    private Utils utils;

    @Test
    public void testEncryptPassword_validString_encryptedString() {

        //Prepare method dependencies
        utils = new Utils();

        //Execute method
        String rs = utils.encryptPassword("example123");

        //Asserts
        assertNotNull(rs);
        assertTrue(rs.equals("7df065c23f49f57077f9113611d6d877"));

    }

    @Test
    public void testEncryptPassword_nullString_nullPointerException() {

        //Prepare method dependencies

        //Execute method

        //Asserts
        assertThrows(NullPointerException.class, () -> utils.encryptPassword(null));

    }
}