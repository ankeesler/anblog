package com.marshmallow.anblog;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTFilterTest {
  /*
  {
    "typ": "JWT",
    "alg": "HS256"
  }
  {
    "sub": "ankeesler",
    "iat": 1584291055,
    "exp": 9999999999
  }
  secret = secret
  */
  private static final String GOOD_TOKEN
          = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmtlZXNsZXIiLCJpYXQiOjE1ODQyOT"
          + "EwNTUsImV4cCI6OTk5OTk5OTk5OX0.CXNFlcjNO6Xp17ADpWdSvDkDKucdcPMmr38HQpW_sBo";
  
  @Test
  public void itWorks() throws ServletException, IOException {
    final AuthenticationSetter authenticationSetter = Mockito.mock(AuthenticationSetter.class);
    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken("ankeesler", "");
    final TokenValidator tokenValidator = Mockito.mock(TokenValidator.class);
    Mockito.when(tokenValidator.validate(GOOD_TOKEN)).thenReturn("ankeesler");
    final JWTFilter jwtFilter = new JWTFilter(authenticationSetter, tokenValidator);
    
    final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    Mockito.when(req.getHeader("Authorization")).thenReturn("Bearer " + GOOD_TOKEN);
    final HttpServletResponse rsp = Mockito.mock(HttpServletResponse.class);
    final FilterChain filterChain = Mockito.mock(FilterChain.class);
    
    jwtFilter.doFilterInternal(req, rsp, filterChain);
    
    Mockito.verify(tokenValidator).validate(GOOD_TOKEN);
    Mockito.verify(authenticationSetter).setAuthentication(usernamePasswordAuthenticationToken);
  }
  
}