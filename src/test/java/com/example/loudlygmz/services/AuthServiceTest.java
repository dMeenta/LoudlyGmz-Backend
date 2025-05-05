package com.example.loudlygmz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.entity.AuthRequests.LoginRequest;
import com.example.loudlygmz.entity.AuthRequests.RegisterRequest;
import com.example.loudlygmz.services.Auth.AuthService;
import com.example.loudlygmz.utils.ApiResponse;
import com.example.loudlygmz.utils.FirebaseAuthClient;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private FirebaseAuthClient firebaseAuthClient;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_shouldReturnSuccessResponse() throws IOException {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        Map<String, String> firebaseResponse = Map.of("email", "test@example.com", "localId", "12345");

        when(firebaseAuthClient.signInWithEmailAndPassword(request.getEmail(), request.getPassword()))
        .thenReturn(firebaseResponse);

        ResponseEntity<ApiResponse<?>> response = authService.login(request);

        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();

        assertEquals(HttpStatus.OK.value(), apiResponse.getStatusCode());
        assertTrue(apiResponse.isSuccess());
        assertEquals("Autenticación exitosa", apiResponse.getMessage());
    }

    @Test
    void register_shouldReturnSuccessResponse() throws IOException {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        Map<String, String> firebaseResponse = Map.of("email", "test@example.com", "localId", "12345");

        when(firebaseAuthClient.signUpWithEmailAndPassword(request.getEmail(), request.getPassword()))
        .thenReturn(firebaseResponse);

        ResponseEntity<ApiResponse<?>> response = authService.register(request);

        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();

        assertEquals(HttpStatus.CREATED.value(), apiResponse.getStatusCode());
        assertTrue(apiResponse.isSuccess());
        assertEquals("Usuario Registrado Correctamente", apiResponse.getMessage());
    }

    @Test
    void deleteUser_ShouldReturnOkResponse() throws Exception {
    String uid = "uidExample00333_";

    Mockito.doNothing().when(firebaseAuthClient).deleteUser(uid);

    // Llamar al método real
    ResponseEntity<ApiResponse<?>> response = authService.deleteUser(uid);

    // Validar la respuesta
    assertEquals(HttpStatus.OK, response.getStatusCode());
    ApiResponse<?> apiResponse = response.getBody();
    assertNotNull(apiResponse);
    assertTrue(apiResponse.isSuccess());
    assertEquals("Usuario eliminado de firebase correctamente", apiResponse.getMessage());
    }
}
