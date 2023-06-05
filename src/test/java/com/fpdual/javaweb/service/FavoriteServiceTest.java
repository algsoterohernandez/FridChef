package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FavoriteServiceTest {
    @InjectMocks
    private FavoriteService favoriteService;

    @Mock
    private FridChefApiClient fridChefApiClient;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        favoriteService = new FavoriteService(fridChefApiClient);
    }

    /**
     * Prueba unitaria para el método {@link FavoriteService#addFavorite(int, UserDto)}
     * cuando se intenta agregar una receta a favoritos que aún no está en la lista y se verifica
     * que la receta se agrega correctamente.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testAddFavorite_recipeNotInList_recipeAddedToFavorites() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        UserDto user = new UserDto();
        user.setId(51);
        user.setFavoriteList(new ArrayList<>());

        // Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.createFavorite(idRecipe, user.getId())).thenReturn(true);

        // Ejecución del método bajo prueba
        UserDto result = favoriteService.addFavorite(idRecipe, user);

        // Verificación del resultado
        assertTrue(result.isFavorite(idRecipe));
    }

    /**
     * Prueba unitaria para el método {@link FavoriteService#removeFavorite(int, UserDto)}
     * cuando se intenta agregar una receta de favoritos que ya está en la lista y se verifica
     * que la lista de favoritos no se modifica.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testAddFavorite_recipeInList_favoriteListUnchanged() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        UserDto user = new UserDto();
        user.setId(51);
        user.setFavoriteList(new ArrayList<>());

        // Ejecución del método bajo prueba
        UserDto result = favoriteService.addFavorite(idRecipe, user);

        // Verificación del resultado
        assertEquals(0, result.getFavoriteList().size());
    }

    /**
     * Prueba unitaria para el método {@link FavoriteService#removeFavorite(int, UserDto)}
     * cuando se intenta eliminar una receta de favoritos que está en la lista y se verifica
     * que la receta se elimina correctamente.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */

    @Test
    public void testRemoveFavorite_recipeInList_recipeRemovedFromFavorites() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        UserDto user = new UserDto();
        user.setId(51);
        List<Integer> favoriteList = new ArrayList<>();
        favoriteList.add(idRecipe);
        user.setFavoriteList(favoriteList);

        // Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.deleteFavorite(idRecipe, user.getId())).thenReturn(true);

        // Ejecución del método bajo prueba
        UserDto result = favoriteService.removeFavorite(idRecipe, user);

        // Verificación del resultado
        assertFalse(result.isFavorite(idRecipe));
    }

    /**
     * Prueba unitaria para el método {@link FavoriteService#removeFavorite(int, UserDto)}
     * cuando se intenta eliminar una receta de favoritos que no está en la lista y se verifica
     * que la lista de favoritos no se modifica.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testRemoveFavorite_recipeInList_favoriteListUnchanged() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        UserDto user = new UserDto();
        user.setId(51);
        user.setFavoriteList(new ArrayList<>());

        // Ejecución del método bajo prueba
        UserDto result = favoriteService.removeFavorite(idRecipe, user);

        // Verificación del resultado
        assertEquals(0, result.getFavoriteList().size());
    }
}
