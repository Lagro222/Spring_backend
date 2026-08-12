package  com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.ArtistDTO.ArtistRequestDTO;
import com.store.app.entities.Artist;
import com.store.app.entities.Track;
import com.store.app.repositories.ArtistRepository;

/**
 * ArtistService
 */
@Service
public class ArtistService {

 @Autowired
 private ArtistRepository artist_repo ;

 @Autowired
 private TrackService track_service;

 public List<Artist> getAll(){return artist_repo.findAll();}

 public Artist getById(Long id ){return artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist!!"));}
 public List<Artist> getByName(String name){return artist_repo.findByName(name);}

 public Artist create_artist(ArtistRequestDTO artist){
   Artist new_artist = new Artist();

   new_artist.setName(artist.name());
   new_artist.setGenre(artist.genre());
   new_artist.setCountry(artist.country());

   return artist_repo.save(new_artist);
 }

 public Artist update(Long id , Artist new_args){

   Artist target =  artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist"));

   target.setName(new_args.getName());
   target.setCountry(new_args.getCountry());
   target.setGenre(new_args.getGenre());
   target.setAlbums(new_args.getAlbums());
   target.setTracks(new_args.getTracks());

   return artist_repo.save(target); 

 }

 public void delete(Long id){

   Artist target = artist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such artist, deleting aborted!!"));
   artist_repo.delete(target);

 }

 public Artist addTrack(Long artistId, Long trackId) {
    Artist artist = getById(artistId);
    Track track = track_service.getById(trackId);
    artist.getTracks().add(track);
    return artist_repo.save(artist);
}


}
