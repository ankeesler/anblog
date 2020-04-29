package com.marshmallow.anblog;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
  //@Value("${anblog.auth.username}")
  private String username;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (!this.username.equals(username)) {
      throw new UsernameNotFoundException("username " + username + " does not match expected " + this.username);
    }
    return new User(username, "", Collections.emptyList());
  }
}
