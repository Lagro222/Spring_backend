package com.store.app.DTOs.userDTO;

// import java.util.List;
// import java.util.Set;

// import com.store.app.entities.Artist;
// import com.store.app.DTOs.artistDTO.ArtistResponseDTO;
// import com.store.app.DTOs.tracksDTO.TrackResponseDTO

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {

  private Long id;
  private String name;
  private String firstname;
  // private Set<Artist> following_artists; 
}
