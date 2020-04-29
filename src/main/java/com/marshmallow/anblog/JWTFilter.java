package com.marshmallow.anblog;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {
  
  private static final String TOKEN_PREFIX = "Bearer ";
  
  @Autowired
  private final AuthenticationSetter authenticationSetter;
  
  @Autowired
  private final TokenValidator tokenValidator;
  
  public JWTFilter(final AuthenticationSetter authenticationSetter,
                   final TokenValidator tokenValidator) {
    this.authenticationSetter = authenticationSetter;
    this.tokenValidator = tokenValidator;
  }
  
  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    final String authorizationHeaderValue = request.getHeader("Authorization");
    if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(TOKEN_PREFIX)) {
      final String token = authorizationHeaderValue.substring(TOKEN_PREFIX.length());
      final String subject = tokenValidator.validate(token);
      final UsernamePasswordAuthenticationToken authentication
              = new UsernamePasswordAuthenticationToken(subject, "");
      authenticationSetter.setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
}
