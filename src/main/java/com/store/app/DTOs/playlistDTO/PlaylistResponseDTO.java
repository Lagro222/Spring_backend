package com.store.app.DTOs.playlistDTO;

import java.util.List;

import com.store.app.DTOs.tracksDTO.HelperTrackDTO;
// import com.store.app.DTOs.artistDTO.ArtistResponseDTO;
// import com.store.app.DTOs.tracksDTO.TrackResponseDTO;
import com.store.app.enums.PlaylistType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistResponseDTO {

  private Long id;
  private String name;
  private String owner_name;
  private PlaylistType type;
  private boolean isCollaborative;
  private List<HelperTrackDTO> tracks;
  private Integer likes;
}
