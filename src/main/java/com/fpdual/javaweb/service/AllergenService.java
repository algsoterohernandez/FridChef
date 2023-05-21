package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;

import java.util.List;

public class AllergenService {

    private final FridChefApiClient fridChefApiClient;

    public AllergenService(FridChefApiClient fridChefApiClient) {
        this.fridChefApiClient = fridChefApiClient;
    }

    public List<AllergenDto> findAllAllergens () {
        List<AllergenDto> allergenDtoList = null;
        try {
            allergenDtoList = fridChefApiClient.findAllAllergens();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return allergenDtoList;
    }
}
