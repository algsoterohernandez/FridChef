package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.enums.Unit;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.ItemDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para manejar los ingredientes.
 */
public class IngredientService {

    private final FridChefApiClient fridChefApiClient;

    /**
     * Constructor de IngredientService.
     *
     * @param fridChefApiClient instancia de FridChefApiClient para interactuar con la API del backend
     */
    public IngredientService(FridChefApiClient fridChefApiClient) {
        this.fridChefApiClient = fridChefApiClient;
    }

    /**
     * Obtiene una lista de todos los ingredientes.
     *
     * @return lista de IngredientDto que representa todos los ingredientes
     */
    public List<IngredientDto> findAllIngredients() {
        List<IngredientDto> ingredientDtoList = null;
        try {
            ingredientDtoList = fridChefApiClient.findAllIngredients();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return ingredientDtoList;
    }

    /**
     * Busca recetas por ingredientes.
     *
     * @param ingredientList lista de ingredientes por los que buscar recetas
     * @return lista de RecipeDto que representa las recetas encontradas
     */
    public List<RecipeDto> findByIngredients(List<String> ingredientList) {
        List<RecipeDto> recipeList = null;

        try {
            recipeList = fridChefApiClient.findByIngredients(ingredientList);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeList;
    }

    /**
     * Obtiene una lista de todas las unidades de medida.
     *
     * @return lista de ItemDto que representa las unidades de medida
     */
    public List<ItemDto> getAllUnits() {

        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(Unit.MG.toString(), "miligramos"));
        items.add(new ItemDto(Unit.GR.toString(), "gramos"));
        items.add(new ItemDto(Unit.KG.toString(), "kilogramos"));
        items.add(new ItemDto(Unit.ML.toString(), "mililitros"));
        items.add(new ItemDto(Unit.L.toString(), "litros"));
        items.add(new ItemDto(Unit.PIZCA.toString(), "pizca"));
        items.add(new ItemDto(Unit.CUCHARADITA.toString(), "cucharadita"));
        items.add(new ItemDto(Unit.CUCHARADA.toString(), "cucharada"));
        items.add(new ItemDto(Unit.UNIDADES.toString(), "unidades"));
        return items;
    }

    /**
     * Elimina un ingrediente dado su ID.
     *
     * @param id ID del ingrediente a eliminar
     * @return true si el ingrediente se eliminó correctamente, false en caso contrario
     */
    public boolean deleteIngredient(int id) {
        boolean deleted;

        try {
            deleted = fridChefApiClient.deleteIngredient(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        return deleted;

    }

    /**
     * Crea un nuevo ingrediente con el nombre especificado.
     *
     * @param name nombre del ingrediente a crear
     * @return instancia de IngredientDto que representa el ingrediente creado
     * @throws ExternalErrorException si ocurre un error al crear el ingrediente
     */
    public IngredientDto createIngredient(String name) throws ExternalErrorException {
        IngredientDto ingredientDto;

        try {

            ingredientDto = fridChefApiClient.createIngredient(name);


        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return ingredientDto;

    }
}