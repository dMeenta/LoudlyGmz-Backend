package com.example.loudlygmz.services.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "El correo ya está en uso",
                null
            ));
            
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al registrar usuario", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getProfile(String uid) {
        try {
            Optional<User> userOpt = userDAO.findById(uid);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                    HttpStatus.NOT_FOUND.value(), "Perfil no encontrado", "No existe el usuario con ese UID"
                    )
                );
            }
            User user = userOpt.get();
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Perfil encontrado correctamente", user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", e.getMessage()));
        }
    }
}
