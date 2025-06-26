package com.example.loudlygmz.infrastructure.common;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class AuthCleanupFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
    try {
      chain.doFilter(req, res);
    } finally {
      AuthUtils.clear();
    }
  }
  
}
