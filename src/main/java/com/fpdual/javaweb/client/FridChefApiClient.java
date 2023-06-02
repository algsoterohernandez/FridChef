package com.fpdual.javaweb.client;

import com.fpdual.javaweb.enums.HttpStatus;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.AlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jakarta.ws.rs.client.Entity.entity;

/**
 * Cliente para interactuar con la API de FridChef.
 * Permite realizar diversas operaciones relacionadas con usuarios, recetas, ingredientes y categorías.
 */
public class FridChefApiClient {
    private final WebTarget webTarget;

    /**
     * Constructor de la clase FridChefApiClient.
     * Crea una instancia del cliente HTTP utilizando ClientBuilder y establece la URL base de la API.
     */
    public FridChefApiClient() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8081/FridChefWebService/webapi");
    }

    /**
     * Constructor de la clase FridChefApiClient que permite especificar un WebTarget personalizado.
     *
     * @param webTarget WebTarget personalizado para la comunicación con la API.
     */
    public FridChefApiClient(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    /**
     * Crea un nuevo usuario en el sistema a partir del objeto UserDto proporcionado.
     *
     * @param userDto El objeto UserDto con los datos del usuario a crear.
     * @return El objeto UserDto del usuario creado.
     * @throws AlreadyExistsException Si el usuario ya existe en el sistema.
     * @throws ExternalErrorException Si ocurre un error externo durante la creación del usuario.
     */
    public UserDto createUser(UserDto userDto) throws AlreadyExistsException, ExternalErrorException {
        UserDto rs;
        Invocation.Builder builder = webTarget.path("user/create").request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(userDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(UserDto.class);

        } else if (response.getStatus() == HttpStatus.NOT_MODIFIED.getStatusCode()) {
            throw new AlreadyExistsException("El usuario ya existe en el sistema.");

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return rs;
    }

    /**
     * Elimina un usuario mediante su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario a eliminar.
     * @return {@code true} si el usuario se eliminó exitosamente, {@code false} de lo contrario.
     */
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

    /**
     * Busca un usuario por su dirección de correo electrónico y contraseña en la API del backend.
     *
     * @param email    La dirección de correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return El objeto UserDto que representa al usuario encontrado, o null si no se encontró ningún usuario.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con la API del backend.
     */
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

    /**
     * Retorna una lista de todos los ingredientes.
     *
     * @return Lista de objetos IngredientDto que representan los ingredientes encontrados.
     * @throws ExternalErrorException Si ocurre un error al listar los ingredientes.
     */
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

    /**
     * Recupera y devuelve una lista de todos los alérgenos.
     *
     * @return La lista de alérgenos recuperada.
     * @throws ExternalErrorException Si ocurre un error al listar los alérgenos en el servidor externo.
     */
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

    /**
     * Busca recetas por lista de ingredientes.
     *
     * @param ingredientsList Lista de ingredientes por los cuales buscar las recetas.
     * @return Lista de objetos RecipeDto que coinciden con los ingredientes especificados.
     * @throws ExternalErrorException si ocurre un error al buscar las recetas por ingredientes.
     */
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

    /**
     * Busca sugerencias de recetas basadas en una lista de ingredientes.
     *
     * @param ingredientsList Lista de ingredientes utilizados para buscar las sugerencias de recetas.
     * @return Lista de objetos RecipeDto que representan las recetas sugeridas.
     * @throws ExternalErrorException Si ocurre un error al buscar las recetas sugeridas.
     */
    public List<RecipeDto> findRecipeSuggestions(List<String> ingredientsList) throws ExternalErrorException {
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        List<RecipeDto> recipeDtoList;

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

    /**
     * Busca recetas por categoría utilizando el ID de la categoría.
     *
     * @param idCategory ID de la categoría
     * @return Lista de RecipeDto que corresponden a la categoría especificada
     * @throws ExternalErrorException si ocurre un error en la comunicación externa
     */
    public List<RecipeDto> findRecipesByCategory(int idCategory) throws ExternalErrorException {
        List<RecipeDto> recipeDtoList;

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

    /**
     * Crea una nueva receta enviando una solicitud POST al backend.
     *
     * @param recipeDto Objeto RecipeDto que contiene los datos de la receta a crear.
     * @return La receta creada en forma de objeto RecipeDto.
     * @throws ExternalErrorException Si ocurre un error al enviar la solicitud de receta al backend.
     */
    public RecipeDto createRecipe(RecipeDto recipeDto) throws ExternalErrorException {

        RecipeDto rs;
        Invocation.Builder builder = webTarget.path("recipes/").request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(recipeDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            rs = response.readEntity(RecipeDto.class);
        } else {
            throw new ExternalErrorException("Ha ocurrido un error al enviar la solicitud de receta");
        }
        return rs;
    }

    /**
     * Recupera la lista de categorías desde la API.
     * Realiza una solicitud GET a la ruta "category/" de la API para obtener las categorías.
     * Si la respuesta es exitosa, se devuelve la lista de categorías obtenida.
     * Si la respuesta indica que no hay contenido, se devuelve una lista vacía.
     * Si la respuesta indica un error interno del servidor, se lanza una excepción ExternalErrorException.
     *
     * @return La lista de categorías obtenida desde la API.
     * @throws ExternalErrorException Si ocurre un error interno en el servidor al realizar la solicitud.
     */
    public List<CategoryDto> findCategories() throws ExternalErrorException {
        List<CategoryDto> categories = null;
        Response response = webTarget.path("category/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            categories = response.readEntity(new GenericType<List<CategoryDto>>() {
            });
        } else if (response.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            categories = Collections.emptyList();
        } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return categories;
    }

    /**
     * Busca una receta por su ID.
     *
     * @param id el ID de la receta a buscar.
     * @return el objeto RecipeDto que representa la receta encontrada.
     * @throws ExternalErrorException si ocurre un error en la búsqueda de la receta por ID.
     */
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

    /**
     * Obtiene una lista de recetas pendientes de aprobación.
     *
     * @return Una lista de RecipeDto que representa las recetas pendientes.
     * @throws ExternalErrorException Si ocurre un error en la comunicación con el backend externo.
     */
    public List<RecipeDto> findByStatusPending() throws ExternalErrorException {
        List<RecipeDto> recipeDtoList;

        Response response = webTarget.path("recipes/find-pending")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>() {
            });


        } else if (response.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            recipeDtoList = null;

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return recipeDtoList;
    }

    /**
     * Actualiza el estado de una receta con el ID proporcionado.
     *
     * @param id     el ID de la receta que se desea actualizar
     * @param status el nuevo estado de la receta
     * @return el objeto RecipeDto actualizado
     * @throws AlreadyExistsException si el estado de la solicitud no se puede modificar
     * @throws ExternalErrorException si ocurre un error externo durante la operación
     */
    public RecipeDto updateRecipeStatus(int id, String status) throws AlreadyExistsException, ExternalErrorException {
        RecipeDto rs;

        Response response = webTarget.path("recipes/update-status/" + id + "/" + status)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(RecipeDto.class);

        } else if (response.getStatus() == HttpStatus.NOT_MODIFIED.getStatusCode()) {
            throw new AlreadyExistsException("El estado de la solicitud no se ha podido modificar.");

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");

        }
        return rs;
    }

    /**
     * Elimina un ingrediente mediante una solicitud HTTP DELETE al backend.
     *
     * @param id el ID del ingrediente a eliminar
     * @return true si el ingrediente se eliminó correctamente, false en caso contrario
     */
    public boolean deleteIngredient(int id) {
        boolean deleted = false;

        Response response = webTarget.path("ingredients/delete/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            deleted = response.readEntity(boolean.class);

        }
        return deleted;
    }

    /**
     * Crea un nuevo ingrediente con el nombre especificado.
     *
     * @param name el nombre del ingrediente a crear
     * @return el objeto IngredientDto que representa el ingrediente creado
     * @throws ExternalErrorException si se produce un error externo durante la operación
     */
    public IngredientDto createIngredient(String name) throws ExternalErrorException {
        IngredientDto rs;
        Invocation.Builder builder = webTarget.path("ingredients/create/" + name + "/")
                .request(MediaType.APPLICATION_JSON);
        Response response = builder.post(entity(name, MediaType.APPLICATION_JSON));

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            rs = response.readEntity(IngredientDto.class);

        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return rs;
    }

    /**
     * Busca las recetas favoritas del usuario.
     * Realiza una solicitud GET a la ruta "favorite/" del web target y obtiene la lista de recetas favoritas.
     * Si la respuesta es exitosa (código 200 OK), se lee la entidad de respuesta y se devuelve la lista de recetas.
     * Si la respuesta indica que no hay contenido (código 204 No Content), se devuelve una lista vacía.
     * Si la respuesta indica un error, se lanza una excepción de tipo ExternalErrorException con un mensaje descriptivo.
     *
     * @return La lista de recetas favoritas del usuario, o una lista vacía si no hay contenido.
     * @throws ExternalErrorException Si ocurre un error durante la solicitud a la API externa.
     */
    public List<RecipeDto> findFavorites() throws ExternalErrorException {
        List<RecipeDto> recipes;
        Response rs = webTarget.path("favorite/")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (rs.getStatus() == HttpStatus.OK.getStatusCode()) {
            recipes = rs.readEntity(new GenericType<List<RecipeDto>>() {
            });
        } else if (rs.getStatus() == HttpStatus.NO_CONTENT.getStatusCode()) {
            recipes = Collections.emptyList();
        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return recipes;
    }

    /**
     * Crea una nueva valoración para una receta.
     *
     * @param valorationDto Objeto ValorationDto que contiene los datos de la valoración.
     * @throws ExternalErrorException Si ocurre un error al añadir la valoración.
     */
    public void createValoration(ValorationDto valorationDto) throws ExternalErrorException {
        Response response = webTarget.path("recipes/" + valorationDto.getIdRecipe() + "/rating")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(valorationDto));

        if (response.getStatus() != HttpStatus.OK.getStatusCode()) {
            throw new ExternalErrorException("Ha ocurrido un error al añadir la valoración");
        }
    }


    public boolean createFavorite(int idRecipe, int idUser) throws ExternalErrorException{
        boolean favoriteCreated = false;

        Response response = webTarget.path("user/"+idUser+"/favorite/" + idRecipe)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(null));

        if(response.getStatus() == HttpStatus.OK.getStatusCode()){
            favoriteCreated = response.readEntity(boolean.class);
        }else{
            throw new ExternalErrorException("Ha ocurrido un error al añadir favorito en la bd");
        }
        return favoriteCreated;

    }

    public boolean deleteFavorite(int idRecipe, int idUser) throws ExternalErrorException{
        boolean favoriteDeleted = false;

        Response response = webTarget.path("user/"+idUser+"/favorite/" + idRecipe)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == HttpStatus.OK.getStatusCode()) {
            favoriteDeleted = response.readEntity(boolean.class);
        }else{
            throw new ExternalErrorException("Ha ocurrido un error al eliminar favorito en la bd");
        }
        return favoriteDeleted;

    }
}