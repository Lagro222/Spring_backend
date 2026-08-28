package com.store.app.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_album;

  @NotBlank(message = "Please enter a valid title")
  private String title;

  @NotNull(message = "Please enter a valid release year")
  private Integer releaseYear;

  @ManyToMany(mappedBy = "albums")
  @JsonIgnore
  private List<Artist> artists = new ArrayList<>();

  @OneToMany(mappedBy = "album")
  @JsonIgnore
  private List<Track> tracks = new ArrayList<>();

  
}
