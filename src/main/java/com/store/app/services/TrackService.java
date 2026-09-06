package com.store.app.services;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.TrackRequestDTO;
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

  //functions for GET
  public List<Track> getAll(){return track_repository.findAll();}
  public List<Track> getByTitle(String title){return track_repository.findByTitle(title);}
  public Track getById(Long id){return track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track"));}

  //crud functions
  public Track create_track(TrackRequestDTO new_track, User current_user){

    Track track = new Track();
    track.setTitle(new_track.getTitle());
    track.setRelease_date(new_track.getRelease_date());
    track.setOwner(current_user);

    return track_repository.save(track);
  }
  public Track update_track(Long id , TrackRequestDTO updating, User current_user){
    
    Track exist = getById(id);

    boolean is_owner = exist.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;

    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this track");
    }

    exist.setRelease_date(updating.getRelease_date());
    exist.setTitle(updating.getTitle());

    return track_repository.save(exist);
  }

  public void delete_track(Long id,User current_user){
    
    Track exist = getById(id);
    
    boolean is_owner = exist.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this track");
    }

    track_repository.delete(exist);

  }

  //functions for conection with other entities
  public Track assignAlbum(Long trackId, Long albumId){

    Track target_track = getById(trackId);
    Album target_album = album_service.getById(albumId);

    target_track.setAlbum(target_album);

    return track_repository.save(target_track);
  }
  //
  // public Track addArtist(Long trackId , Long artistId){
  //   Track target_track = getById(trackId);
  //   Artist target_artist = artist_service.getById(artistId);
  //
  //   target_track.getArtist().add(target_artist);
  //
  //   return track_repository.save(target_track);
  //
  // }
  //
  // public Track likeTrack(Long trackId, Long userId){
  //
  //   Track target_track = getById(trackId);
  //   User target_user = userService.getById(userId);
  //   target_track.getUsers_liked().add(target_user);
  //   return track_repository.save(target_track);
  // }

  // public Track unlikeTrack(Long trackId, Long userId){
  //
  //   Track target_track = getById(trackId);
  //   User target_user = userService.getById(userId);
  //   target_track.getUsers_liked().remove(target_user);
  //   return track_repository.save(target_track);
  // }
  //
}
