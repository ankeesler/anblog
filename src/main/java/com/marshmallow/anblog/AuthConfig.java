package com.marshmallow.anblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .authorizeRequests().antMatchers("/download").permitAll().and()
            .authorizeRequests().antMatchers("/actuator/*").permitAll().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .httpBasic();
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    String userId = System.getenv("ANBLOG_USERNAME");
    if (userId == null) {
      userId = "anblog_default_username";
    }

    String userPassword = System.getenv("ANBLOG_PASSWORD");
    if (userPassword == null) {
      userPassword = "anblog_default_password";
    }

    auth.inMemoryAuthentication().withUser(userId).password(passwordEncoder().encode(userPassword)).authorities("ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
