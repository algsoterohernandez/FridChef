package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.BadRequestException;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

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
        /*Intenta obtener las recetas llamando al método de la apiclient
        * y se capturará e imprimirá en consola el error si se produce una excepción durante la obtención
        * de las recetas*/
        try {
            recipeDtoList = apiClient.findRecipesByCategory(idCategory);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        //Devuelve la lista de recetas encontradas, será nulo si se produjo el error externo
        return recipeDtoList;
    }

    /**
     * Registra una nueva receta.
     *
     * @param recipeDto RecipeDto que representa la receta a registrar.
     * @return RecipeDto que representa la receta registrada.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API externa.
     */
    public RecipeDto registerRecipe(RecipeDto recipeDto) throws ExternalErrorException {
        try {
            /*Intenta crear la receta llamando al método createRecipe del apiClient
            * si se produce una excepción ExternalErrorException durante la creación de la receta
            * y se imprime un mensaje en la consola y relanza la excepción*/
            recipeDto = apiClient.createRecipe(recipeDto);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        //Devuelve el objeto RecipeDto que representa la receta registrada
        return recipeDto;
    }

    /**
     * Busca una receta por su ID.
     *
     * @param id ID de la receta a buscar.
     * @param onlyAccepted filtrar por las recetas con status ACCEPTED o no
     * @return RecipeDto que representa la receta encontrada.
     */
    public RecipeDto findRecipeById(int id, boolean onlyAccepted) {
        try {
            RecipeDto recipeDto = apiClient.findRecipeById(id, onlyAccepted);
            return recipeDto;
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Busca las recetas favoritas correspondientes a los IDs proporcionados.
     *
     * @param ids los IDs de las recetas favoritas a buscar
     * @return una lista de objetos RecipeDto que representan las recetas favoritas encontradas
     * @throws ExternalErrorException si ocurre un error externo durante la búsqueda de las recetas favoritas
     */
    public List<RecipeDto> findFavorites(List<Integer> ids) throws ExternalErrorException, BadRequestException {
        //Realiza la búsqueda de las recetas favoritas llamando al método del apiClient
        List<RecipeDto> recipes = apiClient.findFavorites(ids);

        //Devuelve la lista de recetas favoritas encontradas
        return recipes;
    }

    /**
     * Busca las recetas más valoradas hasta el límite especificado.
     *
     * @param limit el número máximo de recetas a obtener
     * @return una lista de objetos RecipeDto que representan las recetas más valoradas
     * @throws ExternalErrorException si ocurre un error externo durante la búsqueda de las recetas más valoradas
     */
    public List<RecipeDto> findMostRated(int limit) throws ExternalErrorException {
        //Realiza la busqueda de las recetas más valoradas llamando al método del apiclient
        List<RecipeDto> recipesMostRated = apiClient.findMostRated(limit);
        //Devuelve la lista de las recetas mejor valoradas encontradas
        return recipesMostRated;
    }

    /**
     * Obtiene todas las recetas pendientes.
     *
     * @return Lista de RecipeDto con las recetas pendientes.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API externa.
     */
    public List<RecipeDto> findByStatusPending() throws ExternalErrorException {
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
}