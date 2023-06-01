package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;

import java.util.List;


/**
 * Servicio que maneja las operaciones relacionadas con los alérgenos.
 */
public class AllergenService {

    private final FridChefApiClient fridChefApiClient;

    /**
     * Constructor de la clase AllergenService.
     *
     * @param fridChefApiClient el cliente de la API de FridChef
     */
    public AllergenService(FridChefApiClient fridChefApiClient) {
        this.fridChefApiClient = fridChefApiClient;
    }

    /**
     * Retorna una lista de objetos AllergenDto que representan todos los alérgenos disponibles.
     * @return Lista de objetos AllergenDto que representan todos los alérgenos disponibles.
     */
    public List<AllergenDto> findAllAllergens () {
        List<AllergenDto> allergenDtoList = null;
        try {
            allergenDtoList = fridChefApiClient.findAllAllergens();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return allergenDtoList;
    }
}
