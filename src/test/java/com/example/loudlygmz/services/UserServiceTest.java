package com.example.loudlygmz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.DAO.IUserDAO;
import com.example.loudlygmz.entity.User;
import com.example.loudlygmz.services.User.UserService;
import com.example.loudlygmz.utils.ApiResponse;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    IUserDAO userDAO;

    @InjectMocks
    UserService userService;

    @Test
    void createUser_shouldReturnCreatedResponse(){
        User inputUser = new User();
        inputUser.setUid("123");
        inputUser.setEmail("test@email.com");
        inputUser.setProfilePicture("profile_picture");
        inputUser.setUsername("<us\"er>");
        inputUser.setBiography(" <bio> ");
        
        User expectedUser = new User();
        expectedUser.setUid("123");
        expectedUser.setEmail("test@email.com");
        expectedUser.setProfilePicture("profile_picture");
        expectedUser.setUsername("user");
        expectedUser.setBiography("bio");


        Mockito.when(userDAO.save(any(User.class))).thenReturn(expectedUser);

        ResponseEntity<ApiResponse<?>> response = userService.createUser(inputUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario creado exitosamente", response.getBody().getMessage());

        User resultUser = (User) response.getBody().getData();
        assertEquals("user", resultUser.getUsername());
        assertEquals("bio", resultUser.getBiography());
    }
    
    @Test
    void getProfile_ShouldReturnNotFoundResponse(){
        String uid = "estaEsUnaUidInvalida_2003*_";

        Mockito.when(userDAO.findById(uid)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = userService.getProfile(uid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Perfil no encontrado", response.getBody().getMessage());
    }

}
