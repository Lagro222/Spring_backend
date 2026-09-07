package com.store.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
      .authorizeHttpRequests(auth -> auth
          
          //public
          .requestMatchers("/auth/**").permitAll()
          .requestMatchers(HttpMethod.GET,"/tracks/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/playlists/**").permitAll()

          //admin
          .requestMatchers(HttpMethod.DELETE,"/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/tracks/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/albums/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.DELETE, "/playlists/**").hasRole("ADMIN")

          
          .requestMatchers(HttpMethod.POST , "/artists/**").hasRole("ADMIN")
          .requestMatchers(HttpMethod.POST , "/albums/**").hasAnyRole("ARTIST", "ADMIN")
          .requestMatchers(HttpMethod.POST , "/playlists/**").hasAnyRole("USER","ARTIST", "ADMIN")
          .requestMatchers(HttpMethod.POST,"tracks/**").hasAnyRole("ARTIST","ADMIN")



          

          )


  }
}
