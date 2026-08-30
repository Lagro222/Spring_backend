package com.store.app.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistRequestDTO {
  
  @NotBlank(message = "Please enter a valid name")
  private String name;
  
  @NotNull(message = "Please enter a valid release year")
  private Integer releaseYear;

  @NotNull(message = "Please enter a valid type")
  private String playlistType;

  private Boolean isCollaborative = false;
}
