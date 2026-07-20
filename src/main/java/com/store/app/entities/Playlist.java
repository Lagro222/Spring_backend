package com.store.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.app.enums.PlaylistType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Playlist
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_playlist;

  private String name ;
  private PlaylistType type; 

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;

  @ManyToMany
  @JoinTable(
    name = "playlist_track",
    joinColumns = @JoinColumn(name = "id_playlist"),
    inverseJoinColumns = @JoinColumn(name = "id_track")
  )
  @JsonIgnore
  private List<Track> playlist_tracks = new ArrayList<>();
  
}



