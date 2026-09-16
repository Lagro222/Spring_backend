package com.store.app.DTOs.artistDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistResponseDTO {

  private Long id;
  private String name;
  private String genre;
  private String courtry;
}
