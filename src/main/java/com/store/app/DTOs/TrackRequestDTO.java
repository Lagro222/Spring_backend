package com.store.app.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackRequestDTO {
  
  @NotBlank(message = "Please enter a valid title")
  private String title;

  @NotNull(message = "Please enter a valid release date")
  private Integer releaseDate;
}
