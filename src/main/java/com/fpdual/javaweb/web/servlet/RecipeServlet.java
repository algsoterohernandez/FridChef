package com.fpdual.javaweb.web.servlet;
import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.RecipeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "RecipeServlet", urlPatterns = "/recipes")
public class RecipeServlet extends HttpServlet {

    private RecipeService recipeService;

    @Override
    public void init() {
        recipeService = new RecipeService(new FridChefApiClient());
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = recipeService.findRecipeByCategory();
        req.setAttribute("RecipeList", recipes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("recipe.jsp");
        dispatcher.forward(req, resp);
    }*/
}

