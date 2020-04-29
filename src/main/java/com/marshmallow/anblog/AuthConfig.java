package com.marshmallow.anblog;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
  
  //@Autowired
  //private MyUserDetailsService myUserDetailsService;

  @Autowired
  private JWTFilter jwtFilter;
  
  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .authorizeRequests().antMatchers("/download").permitAll().and()
            .authorizeRequests().antMatchers("/actuator/*").permitAll().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .httpBasic().disable();
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
  
  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    auth
            .inMemoryAuthentication()
            .withUser("ankeesler")
            .password(passwordEncoder().encode("ankeesler"))
            .authorities("ADMIN");
  }
  
  // TODO: do we need this?
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
