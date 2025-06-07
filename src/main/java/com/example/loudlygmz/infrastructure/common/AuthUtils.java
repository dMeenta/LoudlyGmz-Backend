package com.example.loudlygmz.infrastructure.common;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.loudlygmz.application.exception.UnauthorizedException;
import com.example.loudlygmz.domain.model.MsqlUser;

public class AuthUtils {
  public static MsqlUser getCurrentUser(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof MsqlUser user) {
      return user;
    }
    
    throw new UnauthorizedException("Token inválido o usuario no autenticado");
  }
}
