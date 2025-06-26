package com.example.loudlygmz.infrastructure.common;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.loudlygmz.application.exception.UnauthorizedException;
import com.example.loudlygmz.domain.model.MsqlUser;

public class AuthUtils {

  private static final ThreadLocal<MsqlUser> currentUserCache = new ThreadLocal<>();

  public static MsqlUser getCurrentUser(){
    if (currentUserCache.get() != null) {
      return currentUserCache.get();
    }
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof MsqlUser user) {
      currentUserCache.set(user);
      return user;
    }
    
    throw new UnauthorizedException("Token inválido o usuario no autenticado");
  }

  public static void clear() {
    currentUserCache.remove();
  }
}
