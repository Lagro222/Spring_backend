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

import jakarta.transaction.Transactional;

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

 private User getUserById(Long id){
  return user_repo.findById(id).orElseThrow(() -> new RuntimeException("no such user"));
 }

 public Artist getById(Long id ){return artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist!!"));}
 public List<Artist> getByName(String name){
    return artist_repo.findByNameContaining(name); 
 }

 public Artist create_artist(ArtistRequestDTO artist, User current_user){
   Artist new_artist = new Artist();

   if(artist_repo.findByName(artist.getName()).isPresent()){
     throw new RuntimeException("Artist with name " + artist.getName() + "already exists !!");
   }

   new_artist.setName(artist.getName());
   new_artist.setGenre(artist.getGenre());
   new_artist.setCountry(artist.getCountry());

   Artist artist_saved = artist_repo.save(new_artist);
   User user = getUserById(current_user.getId_user());

   UserArtistLink link = new UserArtistLink();
   link.setUser(user);
   link.setArtist(artist_saved);
   link.setRole(ArtistRole.OWNER);
     
    userArtistLink_repo.save(link);

    return artist_saved;
 }
public boolean UserControlArtist(User user, Artist artist){
    return userArtistLink_repo.findByUserAndArtist(user, artist).map(link -> link.getRole() != ArtistRole.MEMBER).orElse(false);
}

// @Transactional
 public Artist update(Long id , ArtistRequestDTO new_args , User current_user){

   Artist target =  getById(id);
   User user = getUserById(current_user.getId_user()); 
  
   System.out.println("user id :" + user.getId_user());

   if(!UserControlArtist(user, target)){
     throw new RuntimeException("you are not allowed to update this artist profile");
   }

   target.setName(new_args.getName());
   target.setCountry(new_args.getCountry());
   target.setGenre(new_args.getGenre());
  

   return artist_repo.save(target); 

 }

 public void delete(Long id){

   Artist target = artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist, deleting aborted!!"));
   artist_repo.delete(target);

 }

 public Artist addTrack(Long artistId, Long trackId,User current_user) {
    Artist artist = getById(artistId);
    User  user = getUserById(current_user.getId_user());
    Track track = track_service.findById(trackId);
    
    if(!UserControlArtist(user, artist)){
      throw new RuntimeException("you are not allowed to add this track to this artist profile");
    }
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
