package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;

import java.util.List;

/**
 * Servicio para gestionar las valoraciones.
 */
public class ValorationService {
    private final FridChefApiClient apiClient; // Cliente de la API utilizado para interactuar con el backend

    /**
     * Constructor de la clase ValorationService.
     * @param apiClient Cliente de la API utilizado para interactuar con el backend.
     */
    public ValorationService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Crea una nueva valoración.
     * @param valorationDto Objeto ValorationDto que contiene los datos de la valoración a crear.
     * @throws ExternalErrorException Si ocurre algún error durante la creación de la valoración en la API.
     */
    public boolean createValoration(ValorationDto valorationDto) throws ExternalErrorException {
        boolean createdValoration = true;
        try {
          apiClient.createValoration(valorationDto); // Llama al método createValoration del cliente de la API para crear la valoración
        } catch (ExternalErrorException e) {
            createdValoration = false;
        }
        return createdValoration;
    }

    public List<ValorationDto> findValorations(int idRecipe, int limit) throws ExternalErrorException{
        List<ValorationDto> valorationList;
        try{
            valorationList = apiClient.findValorations(idRecipe, limit);
        }catch(ExternalErrorException e){
            System.out.println("Ha ocurrido un error al buscar la valoración");
            throw e;
        }
        return valorationList;
    }
}