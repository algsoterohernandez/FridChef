package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;

import java.util.List;

/**
 * Servicio para gestionar las valoraciones.
 */
public class ValorationService {
    private final FridChefApiClient apiClient;

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

        /*Llama al método createValoration del cliente de la API para crear la valoración
        y devolverá true o false en función de su respuesta, si se captura alguna excepción
        el resultado será false para indicar que no se ha creado la valoración*/
        try {
          apiClient.createValoration(valorationDto);
        } catch (ExternalErrorException e) {
            createdValoration = false;
        }
        return createdValoration;
    }

    /**
     * Busca las valoraciones de una receta especificada por su ID.
     * En caso de que se genere un error, este será capturado, se emitirá
     * un mensaje a la consola y se relanzará la excepción
     *
     * @param idRecipe el ID de la receta para la cual se desean obtener las valoraciones.
     * @param limit el límite de valoraciones a obtener.
     * @return una lista de objetos {@link ValorationDto} que representan las valoraciones encontradas.
     * @throws ExternalErrorException si ocurre un error externo durante la búsqueda de las valoraciones.
     */
    public List<ValorationDto> findValorations(int idRecipe, int limit) throws ExternalErrorException{
        List<ValorationDto> valorationList;

        try{
            valorationList = apiClient.findValorations(idRecipe, limit);
        }catch(ExternalErrorException e){
            System.out.println("Ha ocurrido un error al buscar la valoración");
            throw e;
        }
        //Se devolverá la lista de valoraciones, en caso de no encontrar valoraciones, devolverá lista vacía
        return valorationList;
    }
}