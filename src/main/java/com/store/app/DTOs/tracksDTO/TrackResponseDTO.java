package com.store.app.DTOs.tracksDTO;

import java.util.List;

import com.store.app.DTOs.albumDTO.HelperAlbumDTO;
// import com.store.app.entities.Artist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackResponseDTO {

  private Long id;
  private String title;
  private Integer release_date;
  private String owner_name;
  private HelperAlbumDTO album;
  private List<String> artists;

}
