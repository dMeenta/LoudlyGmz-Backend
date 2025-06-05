package com.example.loudlygmz.infrastructure.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.service.IMsqlUserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {
  private final FirebaseAuth firebaseAuth;
  private final IMsqlUserService msqlUserService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain)
      throws ServletException, IOException {
        
        String idToken = extractIdToken(request);

        if(idToken!=null){
          try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            MsqlUser user = msqlUserService.getMsqlUserByUid(uid);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              user, null, List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          } catch (FirebaseAuthException fae) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Firebase Token");
            return;
          }
        }
        filterChain.doFilter(request, response);
  }

  private String extractIdToken(HttpServletRequest request) {
    // 1. Header Authorization
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    // 2. Cookie "session"
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("session".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
