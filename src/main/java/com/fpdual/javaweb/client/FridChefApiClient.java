package com.fpdual.javaweb.client;

import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.client.Entity.entity;

public class FridChefApiClient {
    private final WebTarget webTarget;

    public FridChefApiClient() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8081/FridChefWebService/webapi");
    }

    public UserDto createUser(UserDto userDto) throws UserAlreadyExistsException, ExternalErrorException {

        UserDto rs = null;
        Response response = webTarget.path("user/create")
                .request(MediaType.APPLICATION_JSON)
                .post(entity(userDto, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            rs = response.readEntity(UserDto.class);
        } else if (response.getStatus() == 304) {
            throw new UserAlreadyExistsException("El usuario ya existe en el sistema.");
        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return rs;
    }

    public boolean deleteUser(String email) {
        boolean deleted = false;

        String path = "user/delete/".concat(email);
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == 200) {
            deleted = response.readEntity(boolean.class);

        } else if (response.getStatus() == 500) {
            deleted = false;
        }
        return deleted;
    }

    public UserDto findUser(String email, String password) throws ExternalErrorException {
        UserDto rs;
        UserDto rq = new UserDto();
        rq.setEmail(email);
        rq.setPassword(password);

        Response response = webTarget.path("user/find")
                .request(MediaType.APPLICATION_JSON)
                .post(entity(rq, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            rs = response.readEntity(UserDto.class);
        } else if (response.getStatus() == 204) {
            rs = null;
        } else {
            throw new ExternalErrorException("Ha ocurrido un error");
        }
        return rs;
    }
}