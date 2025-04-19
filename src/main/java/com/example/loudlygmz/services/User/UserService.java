package com.example.loudlygmz.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.IUserDAO;
import com.example.loudlygmz.entity.User;
import com.example.loudlygmz.utils.ApiResponse;
import com.example.loudlygmz.utils.SanitizationUtils;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserDAO userDAO;

    @Override
    public ResponseEntity<ApiResponse<User>> createUser(User user) {
        try {
            User sanitized = SanitizationUtils.sanitizeUser(user);

            User response = userDAO.save(sanitized);

            return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED.value(), "Usuario creado exitosamente", response));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al registrar usuario", null));
            
        }
    }
}
