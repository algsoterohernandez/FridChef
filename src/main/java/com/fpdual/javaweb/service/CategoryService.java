package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    private final FridChefApiClient apiClient;

    public CategoryService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CategoryDto> getCategories() {
        ArrayList<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto(1, "Arroz"));
        categories.add(new CategoryDto(2, "Pescados"));
        return categories;
    }
}
