package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;

import java.util.List;

/**
 * Servicio para manejar las recetas favoritas de un usuario.
 */
public class FavoriteService {
    private final FridChefApiClient apiClient;

    /**
     * Constructor de FavoriteService.
     *
     * @param apiClient instancia de FridChefApiClient para interactuar con la API del backend
     */
    public FavoriteService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Agrega una receta a la lista de favoritos de un usuario.
     * Para ello, Se verifica si el idReceta ya está en la lista de favoritos del usuario.
     * Si no se encuentra en la lista entonces llama al método de la API para agregar
     * el idReceta como favorita y si esta se ha añadido correctamente entonces
     * se actualiza la lista de favoritos del usuario con el nuevo idReceta
     *
     * @param idRecipe ID de la receta a agregar
     * @param user     instancia de UserDto que representa al usuario
     * @return instancia de UserDto actualizada con la receta agregada a la lista de favoritos
     */
    public UserDto addFavorite(int idRecipe, UserDto user) {
        try {
            if (!user.isFavorite(idRecipe)) {
                boolean favoriteAdded = apiClient.createFavorite(idRecipe, user.getId());

                if (favoriteAdded) {
                    user = addRecipeToFavoriteList(idRecipe, user);
                }
            }
        } catch (ExternalErrorException e) {
            //en caso de error al interactuar con la API se captura la excepción
            System.out.println(e.getMessage());
        }
        //Se devuelve el UserDto con los datos de la lista actualizados
        return user;
    }

    /**
     * Se comprueba si el id de la receta está ya en la lista, Si no está se agrega a la lista
     * de favoritos del usuario  y se setea para actualizar la "nueva lista" con el valor
     * agregado al UserDto
     *
     * @param idRecipe ID de la receta a agregar
     * @param user     instancia de UserDto que representa al usuario
     * @return instancia de UserDto actualizada con la receta agregada a la lista de favoritos
     */
    private UserDto addRecipeToFavoriteList(int idRecipe, UserDto user) {
        List<Integer> favoriteList = user.getFavoriteList();
        if (!favoriteList.contains(idRecipe)) {
            favoriteList.add(idRecipe);
            user.setFavoriteList(favoriteList);
        }
        //Se devuelve el UserDto con la lista actualizada
        return user;
    }

    /**
     * Elimina una receta de la lista de favoritos de un usuario.
     * Se verifica si el idReceta ya está en la lista de favoritos del usuario.
     * si se encuentra en la lista entonces llama al método de la API para eliminar
     * el idReceta de favorito y si esta se ha eliminado correctamente entonces
     * se actualiza la lista de favoritos del usuario sin el idReceta
     *
     * @param idRecipe ID de la receta a eliminar
     * @param user     instancia de UserDto que representa al usuario
     * @return instancia de UserDto actualizada con la receta eliminada de la lista de favoritos
     */
    public UserDto removeFavorite(int idRecipe, UserDto user) {
        try {
            if (user.isFavorite(idRecipe)) {
                boolean favoriteRemoved = apiClient.deleteFavorite(idRecipe, user.getId());
                if (favoriteRemoved) {
                    user = removeRecipeToFavoriteList(idRecipe, user);
                }
            }
        } catch (ExternalErrorException e) {
            //en caso de error al interactuar con la API se captura la excepción
            System.out.println(e.getMessage());
        }
        //Se devuelve el UserDto con los datos de la lista actualizados
        return user;
    }

    /**
     * Se obtiene la lista actual de favoritos del usuario y se comprueba
     * si el id de la receta está en la lista, Si está se elimina de la lista
     * de favoritos del usuario  y se setea para actualizar la "nueva lista" sin el idRecipe al UserDto
     *
     * @param idRecipe ID de la receta a eliminar
     * @param user     instancia de UserDto que representa al usuario
     * @return instancia de UserDto actualizada con la receta eliminada de la lista de favoritos
     */
    private UserDto removeRecipeToFavoriteList(int idRecipe, UserDto user) {

        List<Integer> favoriteList = user.getFavoriteList();
        if (favoriteList.contains(idRecipe)) {
            favoriteList.remove(favoriteList.indexOf(idRecipe));
            user.setFavoriteList(favoriteList);
        }
        return user;
    }
}
