package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@MultipartConfig
@WebServlet(name = "AddRecipeServlet", urlPatterns = {"/add-recipes"})
public class AddRecipeServlet extends ParentServlet {
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private FridChefApiClient apiClient;

    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        ingredientService = new IngredientService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);

        req.setAttribute("categories", categoryService.getAllCategories());
        req.setAttribute("ingredients", ingredientService.findAllIngredients());
        req.setAttribute("units", ingredientService.getAllUnits());
        req.setAttribute("recipe_created", false);
        req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);

        String name = req.getParameter("title");
        String description = req.getParameter("description");
        int difficulty = Integer.parseInt(req.getParameter("difficulty"));
        int time = Integer.parseInt(req.getParameter("time"));
        String unitTime = req.getParameter("unit_time");
        int idCategory = Integer.parseInt(req.getParameter("category"));
        String[] ingredients = req.getParameterValues("ingredient[]");
        String[] quantity = req.getParameterValues("quantity[]");
        String[] unit = req.getParameterValues("unit[]");


        //Se crea una lista de tipo IngredientRecipeDto con stream
        List<IngredientRecipeDto> ingredientsRecipe = IntStream.range(0, ingredients.length)
                .mapToObj(i -> new IngredientRecipeDto(ingredients[i], quantity[i], unit[i]))
                .collect(Collectors.toList());

        //Obtenemos archivo de la imagen y la convertimos a un inputStream y pasamos a cadena de bytes

        Part imagePart = req.getPart("image");
        String imageBase64;

        InputStream imageInputStream = imagePart.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = imageInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        if (bytesRead != -1) {
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } else {
            imageBase64 = "null";
        }



        //Se crea un objeto RecipeDto con los datos del formulario
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeDto.setDescription(description);
        recipeDto.setDifficulty(difficulty);
        recipeDto.setTime(time);
        recipeDto.setUnitTime(unitTime);
        recipeDto.setIdCategory(idCategory);
        recipeDto.setIngredients(ingredientsRecipe);
        recipeDto.setImageBase64(imageBase64);


        //Se llama al service para crear la receta
        try {
            recipeService.registerRecipe(recipeDto);
        } catch (ExternalErrorException e) {
            throw new RuntimeException(e);
        }

        //Se setean los atributos para la vista
        req.setAttribute("categories", categoryService.getAllCategories());
        req.setAttribute("ingredients", ingredientService.findAllIngredients());
        req.setAttribute("units", ingredientService.getAllUnits());
        req.setAttribute("recipe_created", true);
        req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);
    }
}