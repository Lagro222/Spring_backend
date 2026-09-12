package com.store.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    httpSecurity
      .csrf(csrf -> csrf.disable())
      .exceptionHandling(ex -> ex
          .authenticationEntryPoint((req, res, authException) -> {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"status\": 401 ,\"message\":\"Unauthorized - please login\",\"errors\":[]}");
          })
          )
      .authorizeHttpRequests(auth -> auth
          
          //public
          .requestMatchers("/auth/**").permitAll()
          .requestMatchers(HttpMethod.GET,"/tracks/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/playlists/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/users/**").permitAll()

          //admin
          .requestMatchers(HttpMethod.POST,"/artists/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.PUT,"/artists/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE,"/artists/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE,"/albums/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE,"/tracks/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE,"/playlists/**").hasRole("ADMIN")
          
          //artist and admin 
  
          .requestMatchers(HttpMethod.POST,"/tracks/**").hasAnyRole("ARTIST","ADMIN")
          .requestMatchers(HttpMethod.PUT,"/tracks/**").hasAnyRole("ARTIST","ADMIN")
          .requestMatchers(HttpMethod.POST,"/albums/**").hasAnyRole("ARTIST?","ADMIN")
          .requestMatchers(HttpMethod.PUT,"/albums/**").hasAnyRole("ARTIST?","ADMIN")

          //any logged in user
         .requestMatchers(HttpMethod.POST,"/playlists/**").authenticated()
         .requestMatchers(HttpMethod.POST,"/playlists/create").authenticated()
         .requestMatchers(HttpMethod.PUT,"/playlists/**").authenticated()
         .requestMatchers(HttpMethod.POST, "/users/**").authenticated()
         .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
         .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()

         .anyRequest().authenticated()

      ).sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManger(AuthenticationConfiguration config) throws Exception{
    return config.getAuthenticationManager(); 
  }



}
