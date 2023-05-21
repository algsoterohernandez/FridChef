package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final FridChefApiClient apiClient;

    public CategoryService(FridChefApiClient apiClient) {
this.apiClient = apiClient;
    }

    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categories = new ArrayList<>();
        try {
            categories = apiClient.findCategories();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
}
