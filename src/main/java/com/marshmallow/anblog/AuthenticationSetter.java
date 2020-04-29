package com.marshmallow.anblog;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSetter {
  public void setAuthentication(final Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
