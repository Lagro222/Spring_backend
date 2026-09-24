package com.store.app.security;


import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AccessHandler
 */
@Component
public class AccessHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request , 
      HttpServletResponse response,
      AccessDeniedException accessDeniedException
  ) throws IOException{
    
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.getWriter().write("{\"ERROR\":\"You are not allowed to send this request\" }" );

 }

  
}
