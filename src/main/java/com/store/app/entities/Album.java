package com.store.app.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

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

  private String title;
  private Integer releaseYear;

  @ManyToMany(mappedBy = "albums")
  private List<Artist> artists = new ArrayList<>();


  
}
