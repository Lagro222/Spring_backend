package com.store.app.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequestDTO {

  @NotBlank(message = "Please enter a valid title")
  private String title;

  @NotNull(message = "Please enter a valid release year")
  private Integer releaseYear;
}
