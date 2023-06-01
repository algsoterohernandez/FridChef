
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que maneja las operaciones de administración de ingredientes.
 */
@WebServlet(name = "IngredientAdminServlet", urlPatterns = {"/admin-ingredients"})
public class IngredientAdminServlet extends ParentServlet {
    private IngredientService ingredientService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y IngredientService para manejar los ingredientes.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método para manejar las solicitudes HTTP GET al servlet.
     * Rellena las categorías en la solicitud HTTP recibida.
     * Obtiene la lista de ingredientes utilizando el servicio de ingredientes.
     * Establece los atributos de la solicitud con la lista de ingredientes.
     * Envía la solicitud a la página JSP correspondiente para mostrar la lista de ingredientes.
     * En caso de excepción, redirige la respuesta a la página de inicio del sistema.
     *
     * @param req  la solicitud HTTP recibida.
     * @param resp la respuesta HTTP que se enviará.
     * @throws ServletException si se produce un error en el servlet.
     * @throws IOException      si se produce un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            this.fillCategories(req);

            List<IngredientDto> ingredients = ingredientService.findAllIngredients();
            req.setAttribute("IngredientList", ingredients);

            req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            resp.sendRedirect("/FridChef/home");
        }
    }

    /**
     * Método que maneja las solicitudes POST enviadas al servlet.
     * Realiza acciones dependiendo del valor del parámetro "method-type" en la solicitud.
     * Si el valor es "add", llama al método AddIngredient() para agregar un ingrediente.
     * Si el valor es "delete", llama al método DeleteIngredient() para eliminar un ingrediente.
     * Después de realizar la acción, obtiene la lista actualizada de ingredientes y la establece como atributo en la solicitud.
     * Finalmente, redirige la solicitud a la página "/admin/ingredients.jsp".
     *
     * @param req  la solicitud HttpServletRequest enviada al servlet
     * @param resp la respuesta HttpServletResponse que se enviará al cliente
     * @throws ServletException si ocurre un error durante el procesamiento del servlet
     * @throws IOException      si ocurre un error de entrada/salida durante el procesamiento del servlet
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean succeeded = false;

            this.fillCategories(req);


            if (req.getParameter("method-type").equals("add")) {

                AddIngredient(req, resp);

            } else if (req.getParameter("method-type").equals("delete")) {

                DeleteIngredient(req, resp);

            }
        List<IngredientDto> ingredients = ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
    }

    /**
     * Método privado que agrega un nuevo ingrediente.
     *
     * @param req  el objeto HttpServletRequest que representa la solicitud HTTP.
     * @param resp el objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException      si ocurre un error de E/S durante el procesamiento de la solicitud.
     */
    private void AddIngredient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String ingredientName = req.getParameter("ingredient-name");

            IngredientDto createdIngredientDto = ingredientService.createIngredient(ingredientName);

            if (createdIngredientDto != null && createdIngredientDto.isAlreadyExists()) {

                req.setAttribute("error-add", "El ingrediente ya existe en nuestro sistema.");

            } else if (createdIngredientDto == null || createdIngredientDto.getId() == 0) {

                req.setAttribute("error-add", "No se ha podido crear el ingrediente. Vuelva a intentarlo más tarde.");

            } else {
                req.setAttribute("add-success", true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error-add", "No se ha podido efectuar la acción. Vuelva a intentarlo más tarde.");
        }

    }

    /**
     * Método privado que elimina un ingrediente existente.
     *
     * @param req  el objeto HttpServletRequest que representa la solicitud HTTP.
     * @param resp el objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException      si ocurre un error de E/S durante el procesamiento de la solicitud.
     */
    private void DeleteIngredient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try
        {
            String idParam = req.getParameter("ingredient-id");
            int ingredientId = Integer.parseInt(idParam);

            boolean deleteIngredientDto = ingredientService.deleteIngredient(ingredientId);

            if (!deleteIngredientDto) {
                req.setAttribute("error-delete", "No se ha podido borrar el ingrediente.");
            } else {
                req.setAttribute("delete-success", true);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error-delete", "No se ha podido efectuar la acción. Vuelva a intentarlo más tarde.");
        }

    }

}