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
// import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
im

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

  @NotBlank(message = "Please enter a valid name")  
  private String name ;
  @NotNull(message = "Please enter a valid type")
  private PlaylistType type;
  private boolean isCollaborative = false;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;
  
  // @ManyToMany
  // @JoinTable(
  //   name = "playlist_track",
  //   joinColumns = @JoinColumn(name = "id_playlist"),
  //   inverseJoinColumns = @JoinColumn(name = "id_track")
  // )
  // private List<Track> playlist_tracks = new ArrayList<>();

  @OneToMany(mappedBy = "playlist")
  private List<PlaylistTrack> tracks = new ArrayList<>();

  @ManyToMany(mappedBy = "followed_playlists")
  @JsonIgnore
  private List<User> followers = new ArrayList<>();

}



