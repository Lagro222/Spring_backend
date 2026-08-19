package com.store.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PlaylistTrack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist_track")
public class PlaylistTrack {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "playlist_id")
  private Playlist playlist;

  @ManyToOne
  @JoinColumn(name = "track_id")
  private Track track;

  @ManyToOne
  @JoinColumn(name = "added_by")
  private User addedBy;

  private LocalDateTime addedAt;
  private Integer postion;
  
}
