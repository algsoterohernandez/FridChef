package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;


import java.util.List;

/**
 * Servicio que proporciona métodos para interactuar con las recetas a través del cliente de la API.
 */
public class RecipeService {

    private final FridChefApiClient apiClient;// Cliente de la API utilizado para realizar las llamadas al backend

    /**
     * Constructor de RecipeService.
     *
     * @param apiClient Cliente de la API utilizado para realizar las llamadas al backend.
     */
    public RecipeService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Obtiene sugerencias de recetas basadas en una lista de ingredientes.
     *
     * @param ingredientList Lista de ingredientes.
     * @return Lista de RecipeDto con las sugerencias de recetas.
     */
    public List<RecipeDto> findRecipeSuggestions(List<String> ingredientList) {
        List<RecipeDto> recipeSuggestions = null;
        try {
            recipeSuggestions = apiClient.findRecipeSuggestions(ingredientList);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeSuggestions;
    }

    /**
     * Obtiene todas las recetas de una categoría específica.
     *
     * @param idCategory ID de la categoría.
     * @return Lista de RecipeDto con las recetas de la categoría.
     */
    public List<RecipeDto> findAllRecipesByCategoryId(int idCategory) {
        List<RecipeDto> recipeDtoList = null;
        try {
            recipeDtoList = apiClient.findRecipesByCategory(idCategory);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeDtoList;
    }

    /**
     * Registra una nueva receta.
     *
     * @param recipeDto RecipeDto que representa la receta a registrar.
     * @return RecipeDto que representa la receta registrada.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API externa.
     */
    public RecipeDto registerRecipe(RecipeDto recipeDto) throws ExternalErrorException{
        try{
            recipeDto = apiClient.createRecipe(recipeDto);

        }catch (ExternalErrorException e){
            System.out.println(e.getMessage());
            throw e;
        }
        return recipeDto;
    }

    /**
     * Busca una receta por su ID.
     *
     * @param id ID de la receta a buscar.
     * @return RecipeDto que representa la receta encontrada.
     */
    public RecipeDto findRecipeById(int id) {
        try {
            RecipeDto recipeDto = apiClient.findRecipeById(id);
            return recipeDto;
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todas las recetas pendientes.
     *
     * @return Lista de RecipeDto con las recetas pendientes.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API externa.
     */
    public List<RecipeDto> findByStatusPending() throws ExternalErrorException{
        List<RecipeDto> recipeDtoList;

        try {
            recipeDtoList = apiClient.findByStatusPending();

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return recipeDtoList;
    }

    /**
     * Actualiza el estado de una receta.
     *
     * @param id     ID de la receta a actualizar.
     * @param status Nuevo estado de la receta.
     * @return RecipeDto que representa la receta actualizada.
     * @throws Exception Si ocurre un error en la comunicación con la API externa.
     */
    public RecipeDto updateRecipeStatus(int id, String status) throws Exception {
        RecipeDto recipeDto;

        try {
            recipeDto = apiClient.updateRecipeStatus(id, status);

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return recipeDto;
    }

    /**
     * Busca una receta por su ID.
     *
     * @param idRecipe ID de la receta a buscar.
     * @return RecipeDto que representa la receta encontrada.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API externa.
     */
    public RecipeDto findRecipe(int idRecipe) throws ExternalErrorException{
        RecipeDto recipeDto;

        try {
            recipeDto = apiClient.findRecipeById(idRecipe);
        } catch (ExternalErrorException e){
            System.out.println("Ha ocurrido un error al solicitar los datos de la Receta");
            throw e;
        }

        return recipeDto;
    }
}