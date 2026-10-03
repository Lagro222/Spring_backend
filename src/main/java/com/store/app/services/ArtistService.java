package  com.store.app.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.artistDTO.ArtistRequestDTO;
import com.store.app.entities.Album;
import com.store.app.entities.Artist;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.entities.UserArtistLink;
import com.store.app.enums.ArtistRole;
// import com.store.app.entities.User;
// import com.store.app.enums.Role;
import com.store.app.repositories.ArtistRepository;
import com.store.app.repositories.UserArtistLinkRepository;
import com.store.app.repositories.UserRepository;

/**
 * ArtistService
 */
@Service
public class ArtistService {

 @Autowired
 private ArtistRepository artist_repo ;

 @Autowired
 private TrackService track_service;

 @Autowired
 private AlbumService album_service;

 @Autowired
 private UserRepository user_repo;

 @Autowired
 private UserArtistLinkRepository userArtistLink_repo;

 public List<Artist> getAll(){return artist_repo.findAll();}

 public Artist getById(Long id ){return artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist!!"));}
 public List<Artist> getByName(String name){
    return artist_repo.findByName(name); 
 }

 public Artist create_artist(ArtistRequestDTO artist, User current_user){
   Artist new_artist = new Artist();

   new_artist.setName(artist.getName());
   new_artist.setGenre(artist.getGenre());
   new_artist.setCountry(artist.getCountry());

   Artist artist_saved = artist_repo.save(new_artist);
   User user = user_repo.findById(current_user.getId_user())
     .orElseThrow(()-> new RuntimeException("no such user!!"));

   UserArtistLink link = new UserArtistLink();
   link.setUser(user);
   link.setArtist(artist_saved);
   link.setRole(ArtistRole.OWNER);
     
    userArtistLink_repo.save(link);

    return artist_saved;
 }

 public Artist update(Long id , ArtistRequestDTO new_args){

   Artist target =  artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist"));

   // target.setName(new_args.getName());
   target.setCountry(new_args.getCountry());
   target.setGenre(new_args.getGenre());
   
   return artist_repo.save(target); 

 }

 public void delete(Long id){

   Artist target = artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist, deleting aborted!!"));
   artist_repo.delete(target);

 }

 public Artist addTrack(Long artistId, Long trackId) {
    Artist artist = getById(artistId);
    Track track = track_service.findById(trackId);
    artist.getTracks().add(track);
    return artist_repo.save(artist);
  }

  public Set<Track> getTraksByArtist(Long artistId){
    Artist artist = getById(artistId);
    return artist.getTracks();
  }

  public Artist addAlbum(Long artistId, Long albumId) {
    Artist artist = getById(artistId);
    Album album = album_service.findById(albumId);
    artist.getAlbums().add(album);
    return artist_repo.save(artist);
  }


}
