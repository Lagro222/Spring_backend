package com.store.app.entities;

// import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

// import com.store.app.entities.entityListners.UserEntityListener;
import com.store.app.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @EntityListeners(UserEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_user ;
  
  private String name;
  private String firstname;
 
  @Column(unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @JsonIgnore
  private String password;

  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "user_track",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_track")
  )
  private Set<Track> liked = new HashSet<>();

  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "following_artist",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_artist")
  )
  private Set<Artist> followed_Artists = new HashSet<>();

  @ManyToMany
  @JsonIgnore
  @JoinTable(
    name = "following_playlist",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_playlist")
  )
  private Set<Playlist> followed_playlists = new HashSet<>();
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

  //Authontification
  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired(){return true;}

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked(){return true;}

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired(){return true;}

  @Override
  @JsonIgnore
  public boolean isEnabled(){return true;}

  
  }
