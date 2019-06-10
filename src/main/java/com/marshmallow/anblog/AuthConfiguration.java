package com.marshmallow.anblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthConfiguration(
          final AuthenticationConfiguration authenticationConfiguration,
          final PasswordEncoder passwordEncoder
  ) throws Exception {
    this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    String clientId = System.getenv("ANBLOG_CLIENT_ID");
    if (clientId == null) {
      clientId = "anblog_default_client_id";
    }

    String clientSecret = System.getenv("ANBLOG_CLIENT_SECRET");
    if (clientSecret == null) {
      clientSecret = "anblog_default_client_secret";
    }

    clients.inMemory()
            .withClient(clientId)
            .secret(passwordEncoder.encode(clientSecret))
            .authorizedGrantTypes("client_credentials")
            .scopes("post:read", "post:write")
            .accessTokenValiditySeconds(600_000_000);
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
            .authenticationManager(authenticationManager)
            .tokenStore(new InMemoryTokenStore());
  }
}

@Configuration
@EnableWebSecurity
class UserConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .mvcMatchers(HttpMethod.GET, "/posts").hasAuthority("post:read")
            .anyRequest().authenticated()
            .and().oauth2ResourceServer().bearerTokenResolver()
            .and().httpBasic();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    String userId = System.getenv("ANBLOG_USER_ID");
    if (userId == null) {
      userId = "anblog_default_user_id";
    }

    String userPassword = System.getenv("ANBLOG_USER_PASSWORD");
    if (userPassword == null) {
      userPassword = "anblog_default_user_password";
    }

    return new InMemoryUserDetailsManager(
            User.builder()
                    .username(userId)
                    .password(passwordEncoder().encode(userPassword))
                    .roles("ADMIN")
                    .build()
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}