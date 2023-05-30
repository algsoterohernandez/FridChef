package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;

public class ValorationService {
    private final FridChefApiClient apiClient;

    public ValorationService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void createValoration(ValorationDto valorationDto) throws ExternalErrorException {
        try {
            apiClient.createValoration(valorationDto);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
