package com.store.app.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tracks")
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_track;

  private String title;
  private Integer realease_date;

  @ManyToOne
  @JoinColumn(name = "album_id")
  private Album album;

 @ManyToMany(mappedBy = "tracks")
 private List<Artist> artist = new ArrayList<>();

 @ManyToMany(mappedBy = "liked")
 private List<User> users_liked = new ArrayList<>();

 @ManyToMany(mappedBy = "playlist_tracks")
 private List<Playlist> playlists;
}
