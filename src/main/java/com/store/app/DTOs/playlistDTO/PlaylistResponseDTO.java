package com.store.app.DTOs.playlistDTO;

import java.util.List;

import com.store.app.DTOs.artistDTO.ArtistResponseDTO;
import com.store.app.DTOs.tracksDTO.TrackResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistResponseDTO {

  private Long id;
  private String name;
  private List<TrackResponseDTO> tracks;
  private List<ArtistResponseDTO> artists;
}
