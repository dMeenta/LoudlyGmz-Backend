package com.example.loudlygmz.infrastructure.common;

public class OwnerUtils {
  public static Boolean checkOwnership(String usernameLogged, String usernameTarget){
    return usernameLogged.equals(usernameTarget);
  }
}
