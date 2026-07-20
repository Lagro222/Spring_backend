package com.store.app.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * User
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_user ;

  private String name;
  private String firstname;
  private String email;

  @ManyToMany
  @JoinTable(
    name = "user_track",
    joinColumns = "id_user",
    inverseJoinColumns = "id_track"
  )
  private List<Track> liked = new ArrayList<>();
  
}
