package com.store.app.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_user ;

  @NotBlank(message = "Please enter a valid name")
  private String name;

  @NotBlank(message = "Please enter a valid firstname")
  private String firstname;

  @NotBlank(message = "Please enter a valid lastname")
  private String email;


  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "user_track",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_track")
  )
  private List<Track> liked = new ArrayList<>();

  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "following_artist",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_artist")
  )
  private List<Artist> followed_Artists = new ArrayList<>();

  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "following_playlist",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_playlist")
  )
  private List<Playlist> followed_playlists = new ArrayList<>();
  //to avoid conflict for remove() function
  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if(!(o instanceof User)) return false;

    User user = (User) o;
    return id_user != null && id_user.equals(user.id_user);
  }

  @Override
  public int hashCode(){
    return getClass().hashCode();
  }
}
