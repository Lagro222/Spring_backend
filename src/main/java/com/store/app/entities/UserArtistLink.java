package com.store.app.entities;

import com.store.app.enums.ArtistRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_artist_link")
public class UserArtistLink {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_link_id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @Enumerated(EnumType.STRING)
  private ArtistRole role;

}
