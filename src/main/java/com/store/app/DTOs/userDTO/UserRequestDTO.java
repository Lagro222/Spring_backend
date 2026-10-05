package com.store.app.DTOs.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
  
  @NotBlank(message = "please enter a valid name")
  private String name;
 
  @NotBlank(message = "please enter a valid firstname")
  private String firstname;
  
  @NotBlank(message = "please enter a valid email")
  @Email(message = "please enter a valid email")
  private String email;

  @NotBlank(message = "please enter a valid password")
  private String password;
}
