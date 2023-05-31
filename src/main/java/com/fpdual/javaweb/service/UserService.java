package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.AlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;

/**
 * La clase UserService proporciona métodos para realizar operaciones relacionadas con usuarios
 * utilizando el cliente de la API FridChefApiClient.
 */
public class UserService {

    private final FridChefApiClient apiClient;

    /**
     * Constructor de la clase UserService.
     * @param apiClient el cliente de la API FridChefApiClient utilizado para realizar las llamadas al backend.
     */
    public UserService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param userDto el objeto UserDto que contiene la información del usuario a registrar.
     * @return el objeto UserDto registrado, con la indicación de si el usuario ya existe en el sistema.
     * @throws ExternalErrorException si ocurre un error durante la comunicación con la API externa.
     */
    public UserDto registerUser(UserDto userDto) throws ExternalErrorException {
        try {
            userDto = apiClient.createUser(userDto);
        } catch (AlreadyExistsException uaee) {
            if (userDto != null) {
                userDto.setAlreadyExists(true);
            }
        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }
        return userDto;
    }

    /**
     * Elimina un usuario del sistema.
     * @param email el correo electrónico del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false en caso contrario.
     * @throws Exception si ocurre un error durante la eliminación del usuario.
     */
    public boolean unregisterUser(String email){
        boolean deleted;
        try {
            deleted = apiClient.deleteUser(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return deleted;
    }

    /**
     * Busca un usuario en el sistema por su correo electrónico y contraseña.
     * @param email el correo electrónico del usuario a buscar.
     * @param password la contraseña del usuario a buscar.
     * @return el objeto UserDto encontrado, o null si no se encontró ningún usuario con las credenciales proporcionadas.
     * @throws Exception si ocurre un error durante la búsqueda del usuario.
     */
    public UserDto findUser(String email, String password) throws Exception {
        UserDto userDto;
        try {
            userDto = apiClient.findUser(email, password);
        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }
        return userDto;
    }
}