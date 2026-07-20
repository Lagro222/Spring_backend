package  com.store.app.entities;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

 private String name ;
 private String genre;
 private String country;

@ManyToMany
@JoinTable(
  name = "artist_album",
  joinColumns = @JoinColumn(name = "id_artist"  ),
  inverseJoinColumns = @JoinColumn(name = "id_album")
)
@JsonIgnore
 List<Album> albums = new ArrayList<>(); 
 
@ManyToMany
@JoinTable(
  name = "artist_track",
  joinColumns = @JoinColumn(name = "id_artist"),
  inverseJoinColumns = @JoinColumn( name = "id_track") 
)
@JsonIgnore
List<Track> tracks = new ArrayList<>();
}
