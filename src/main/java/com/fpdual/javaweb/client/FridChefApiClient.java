package com.fpdual.javaweb.client;

import com.fpdual.javaweb.enums.HttpStatus;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.client.Entity.entity;

public class FridChefApiClient {
    private final WebTarget webTarget;

    public FridChefApiClient() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8081/FridChefWebService/webapi");
    }

    public FridChefApiClient(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    public UserDto createUser(UserDto userDto) throws UserAlreadyExistsException, ExternalErrorException {
        UserDto rs;
        Invocation.Builder builder = webTarget.path("user/create").request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(userDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(UserDto.class);

        } else if (response.getStatus() == HttpStatus.NOT_MODIFIED.getStatusCode()) {
            throw new UserAlreadyExistsException("El usuario ya existe en el sistema.");

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return rs;
    }

    public boolean deleteUser(String email) {
        boolean deleted = false;

        String path = "user/delete/".concat(email);
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            deleted = response.readEntity(boolean.class);
        }
        return deleted;
    }

    public UserDto findUser(String email, String password) throws ExternalErrorException {
        UserDto rs;
        UserDto rq = new UserDto();
        rq.setEmail(email);
        rq.setPassword(password);

        Response response = webTarget.path("user/find")
                .request(MediaType.APPLICATION_JSON)
                .post(entity(rq, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(UserDto.class);

        } else if (response.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            rs = null;

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return rs;
    }

    public List<IngredientDto> findAllIngredients() throws ExternalErrorException {
        List<IngredientDto> rs = null;
        Response response = webTarget.path("ingredients")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(new GenericType<List<IngredientDto>>() {
            });
        } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ExternalErrorException("Ha ocurrido un error al listar todos los ingredientes");
        }
        return rs;
    }

    public List<AllergenDto> findAllAllergens() throws ExternalErrorException {
        List<AllergenDto> rs = null;
        Response response = webTarget.path("allergens")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(new GenericType<List<AllergenDto>>() {
            });
        } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ExternalErrorException("Ha ocurrido un error al listar todos los alérgenos");
        }
        return rs;
    }

    public List<RecipeDto> findByIngredients(List<String> ingredientsList) throws ExternalErrorException {
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        List<RecipeDto> recipeDtoList;

        Response response = webTarget.path("recipes/findbyingredients")
                .request(MediaType.APPLICATION_JSON)
                .post(entity(recipeFilterDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>() {
            });
        } else {
            throw new ExternalErrorException("Ha ocurrido un error al buscar las recetas por ingredientes");
        }
        return recipeDtoList;

    }

    public List<RecipeDto> findRecipeSuggestions(List<String> ingredientsList) throws ExternalErrorException {
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        List<RecipeDto> recipeDtoList = null;

        Response response = webTarget.path("recipes/findSuggestions")
                .request(MediaType.APPLICATION_JSON)
                .post(entity(recipeFilterDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>() {
            });
        } else {
            throw new ExternalErrorException("Ha ocurrido un error al buscar las recetas sugeridas");
        }
        return recipeDtoList;

    }

    public List<RecipeDto> findRecipesByCategory(int idCategory) throws ExternalErrorException {
        List<RecipeDto> recipeDtoList = null;

        Response response = webTarget.path("category/" + idCategory + "/recipes")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>() {
            });
        } else if (response.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            recipeDtoList = new ArrayList<>();
        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return recipeDtoList;

    }

    public RecipeDto createRecipe(RecipeDto recipeDto) throws ExternalErrorException {

        RecipeDto rs = null;
        Invocation.Builder builder = webTarget.path("recipes/").request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(recipeDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            rs = response.readEntity(RecipeDto.class);
        } else {
            throw new ExternalErrorException("Ha ocurrido un error al enviar la solicitud de receta");
        }
        return rs;
    }

    public List<CategoryDto> findCategories() throws ExternalErrorException {
        List<CategoryDto> categories = null;
        Response response = webTarget.path("category/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            categories = response.readEntity(new GenericType<List<CategoryDto>>() {
            });
        } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return categories;
    }

    public RecipeDto findRecipeById(int id) throws ExternalErrorException {
        Response response = webTarget.path("recipes/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            RecipeDto recipeDto = response.readEntity(RecipeDto.class);
            return recipeDto;
        } else {
            throw new ExternalErrorException("Ha ocurrido un error en la busqueda de recetas por Id");
        }
    }

    public List<RecipeDto> findByStatusPending() throws ExternalErrorException {
        List<RecipeDto> recipeDtoList;

        Response response = webTarget.path("recipes/find-pending")
                .request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>(){});


        } else if (response.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            recipeDtoList = null;

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return recipeDtoList;
    }

    public RecipeDto updateRecipeStatus(int id, String status) throws UserAlreadyExistsException, ExternalErrorException {
        RecipeDto rs;

        Response response = webTarget.path("recipes/update-status/" + id + "/" + status).
                request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(RecipeDto.class);

        } else if (response.getStatus() == HttpStatus.NOT_MODIFIED.getStatusCode()) {
            throw new UserAlreadyExistsException("El estado de la solicitud no se ha podido modificar.");

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return rs;
    }

    public boolean deleteIngredient(int id){
        boolean deleted = false;

        Response response = webTarget.path("ingredient/delete/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            deleted = response.readEntity(boolean.class);

        }
        return deleted;
    }

    public IngredientDto createIngredient(String name) throws ExternalErrorException {
        IngredientDto rs;
        Invocation.Builder builder = webTarget.path("ingredient/create").request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(name, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(IngredientDto.class);
        }
        else
        {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return rs;
    }

}
