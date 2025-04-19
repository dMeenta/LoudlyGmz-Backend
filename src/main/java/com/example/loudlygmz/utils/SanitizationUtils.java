package com.example.loudlygmz.utils;

import com.example.loudlygmz.entity.User;

public class SanitizationUtils {

    public static User sanitizeUser(User user) {
        if (user == null) return null;
        if (user.getUsername() != null) {
            user.setUsername(
                user.getUsername().trim().replaceAll("[<>\"']", "")
            );
        }
    
        if (user.getBiography() != null) {
            user.setBiography(
                user.getBiography().trim().replaceAll("[<>\"']", "")
            );
        }
        
        return user;
    }
}
