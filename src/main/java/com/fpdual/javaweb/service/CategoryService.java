package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que proporciona métodos para gestionar las categorías.
 */

public class CategoryService {
    private final FridChefApiClient apiClient;

    /**
     * Constructor de la clase CategoryService.
     * @param apiClient Cliente de la API utilizado para realizar las llamadas al backend.
     */
    public CategoryService(FridChefApiClient apiClient) {
this.apiClient = apiClient;
    }

    /**
     * Obtiene todas las categorías.
     *
     * @return Una lista de objetos CategoryDto que representa todas las categorías disponibles.
     *         Si se produce un error al obtener las categorías, se imprimirá el mensaje de error y se devolverá una lista vacía.
     */
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categories = new ArrayList<>();
        try {
            categories = apiClient.findCategories();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
}
