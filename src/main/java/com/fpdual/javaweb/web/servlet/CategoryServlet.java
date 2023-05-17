package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;

import java.util.List;

public class CategoryServlet {

    private CategoryService categoryService;

    List<CategoryDto> categories = categoryService.getCategories();

}
