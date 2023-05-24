package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ParentServlet  extends HttpServlet {
    protected CategoryService categoryService;

    public void init(FridChefApiClient apiClient) {
        categoryService = new CategoryService(apiClient);
    }

    protected HttpServletRequest fillCategories(HttpServletRequest req) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        req.setAttribute("categoryList", categories);
        return req;
    }
}
