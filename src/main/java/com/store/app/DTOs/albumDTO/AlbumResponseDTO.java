package com.store.app.DTOs.albumDTO;

import java.util.List;
import java.util.Set;

import com.store.app.DTOs.tracksDTO.TrackResponseDTO;
import com.store.app.entities.Artist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlbumResponseDTO {
  private Long id;
  private String title;
  private Integer releaseYear;
  private String owner_name;
  private Set<Artist> artists;
  private List<TrackResponseDTO> tracks;
}
