package  com.store.app.entities;

// import java.util.List;
import java.util.Set;
// import java.util.ArrayList;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.validation.Valid;
// import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artists")
public class Artist{
  
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_artist ;
 //
 // @OneToOne()
 // @JoinColumn(name = "id_user" , unique = true)
 // private User user;
 private String name ;
 private String genre;
 private String country;
 Boolean verrified = false; 

@ManyToMany
@JoinTable(
  name = "artist_album",
  joinColumns = @JoinColumn(name = "id_artist"  ),
  inverseJoinColumns = @JoinColumn(name = "id_album")
)
@JsonIgnore
private Set<Album> albums = new HashSet<>(); 
 
@ManyToMany
@JoinTable(
  name = "artist_track",
  joinColumns = @JoinColumn(name = "id_artist"),
  inverseJoinColumns = @JoinColumn( name = "id_track") 
)
@JsonIgnore
private Set<Track> tracks = new HashSet<>();

@ManyToMany(mappedBy = "followed_Artists")
@JsonIgnore
private Set<User> followers = new HashSet<>();

}
