package com.example.loudlygmz.application.exception;

import java.util.Map;

public class CategoryValidationException extends RuntimeException {
  private Map<String, String> validationMap;
  
  public CategoryValidationException(Map<String, String> validationMap) {
    super("Algunas categorías no fueron encontradas.");
    this.validationMap = validationMap;
  }

  public Map<String, String> getValidationMap() {
    return validationMap;
  }

}
