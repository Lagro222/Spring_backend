package com.store.app.services;

import java.util.List;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.albumDTO.HelperAlbumDTO;
import com.store.app.DTOs.tracksDTO.TrackRequestDTO;
import com.store.app.DTOs.tracksDTO.TrackResponseDTO;
import com.store.app.entities.Album;
// import com.store.app.entities.Artist;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.Role;
// import com.store.app.entities.User;
import com.store.app.repositories.TrackRepository;

import lombok.RequiredArgsConstructor;

/**
 * TrackService
 */

@Service
@RequiredArgsConstructor
public class TrackService {


  public final TrackRepository track_repository;
  public final AlbumService album_service;
  // public final ArtistService artist_service;
  // public final UserService userService;


  public TrackResponseDTO toDTO(Track track){
   
    HelperAlbumDTO album = track.getAlbum() != null ? 
      new HelperAlbumDTO(
          track.getAlbum().getId_album(),
          track.getAlbum().getTitle(),
          track.getAlbum().getReleaseYear(),
          track.getAlbum().getOwner().getName() 
          ): null;


    return new TrackResponseDTO(
        track.getId_track(),
        track.getTitle(),
        track.getRelease_date(), 
        track.getOwner() != null ? track.getOwner().getName() : null,
        album,
        track.getArtists() 
        );
  }
  //functions for GET
  
  public Track findById(Long id){return track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track"));}


  public List<TrackResponseDTO> getAll(){
    return track_repository.findAll()
      .stream()
      .map(this::toDTO) // so i remmember this::toDTO means track -> this.toDTO(track)  
      .collect(Collectors.toList());
  }
  public List<TrackResponseDTO> getByTitle(String title){
    return track_repository.findByTitle(title)
      .stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }
  public TrackResponseDTO getById(Long id){ 
  
    Track track = track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track"));

    return toDTO(track);
  }

  //crud functions
  public TrackResponseDTO create_track(TrackRequestDTO new_track, User current_user){

    Track track = new Track();
    track.setTitle(new_track.getTitle());
    track.setRelease_date(new_track.getRelease_date());
    track.setOwner(current_user);

    track_repository.save(track);

    return toDTO(track);
  }
  public TrackResponseDTO update_track(Long id , TrackRequestDTO updating, User current_user){
    
    Track exist = track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track // updating stoped."));

    boolean is_owner = exist.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;

    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this track");
    }

    exist.setRelease_date(updating.getRelease_date());
    exist.setTitle(updating.getTitle());

    track_repository.save(exist);

    return toDTO(exist);
  }

  public void delete_track(Long id,User current_user){
    
    Track exist = track_repository.findById(id).orElseThrow(() -> new RuntimeException("no such track // deleting stoped."));
    
    boolean is_owner = exist.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this track");
    }

    track_repository.delete(exist);

  }

  //functions for conection with other entities
  public Track assignAlbum(Long trackId, Long albumId){

    Track target_track =  track_repository.findById(trackId).orElseThrow(()-> new RuntimeException("no such track"));;
    Album target_album = album_service.findById(albumId);

    target_track.setAlbum(target_album);

    return track_repository.save(target_track);
  }
  
}
