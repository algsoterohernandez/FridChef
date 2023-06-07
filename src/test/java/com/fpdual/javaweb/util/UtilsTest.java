package com.fpdual.javaweb.util;

import com.fpdual.javaweb.service.ValorationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase UtilsTest
 */
@ExtendWith(MockitoExtension.class)

public class UtilsTest {

    /**
     * Prueba unitaria para el método encryptPassword().
     * Debe devolver una cadena encriptada no nula cuando se le pasa una cadena válida.
     */
    @Test
    public void testEncryptPassword_validString_encryptedString() {
        //Execute method
        String rs = Utils.encryptPassword("example123");

        //Asserts
        assertNotNull(rs);
        assertTrue(rs.equals("7df065c23f49f57077f9113611d6d877"));

    }

    /**
     * Prueba unitaria para el método encryptPassword().
     * Debe devolver un valor nulo cuando se le pasa una cadena nula.
     */
    @Test
    public void testEncryptPassword_nullString_nullResponse() {

        //Asserts
        String encrypted =  Utils.encryptPassword(null);
        assertNull(encrypted);

    }
}