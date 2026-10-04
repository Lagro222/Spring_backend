package com.store.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.albumDTO.AlbumRequestDTO;
import com.store.app.DTOs.albumDTO.AlbumResponseDTO;
import com.store.app.DTOs.albumDTO.HelperAlbumDTO;
import com.store.app.DTOs.tracksDTO.TrackResponseDTO;
import com.store.app.entities.Album;
import com.store.app.entities.Artist;
import com.store.app.entities.User;
import com.store.app.entities.UserArtistLink;
import com.store.app.enums.ArtistRole;
import com.store.app.enums.Role;
import com.store.app.repositories.AlbumRepository;
// import com.store.app.repositories.TrackRepository;
import com.store.app.repositories.UserArtistLinkRepository;

/**
 * AlbumService
 */

@Service
public class AlbumService {

  @Autowired
  private AlbumRepository album_repo;

  @Autowired
  private UserArtistLinkRepository link_repo;
  // @Autowired
  // private TrackRepository track_repo;

  private AlbumResponseDTO toDTO(Album album){
   
    HelperAlbumDTO albumDTO = new HelperAlbumDTO(
        album.getId_album(), 
        album.getTitle(), 
        album.getReleaseYear(), 
        album.getOwner().getName() != null ? album.getOwner().getName() : null
        );

  List<String> artists = album.getArtists().isEmpty() ? List.of(album.getOwner().getName()) :
      album.getArtists().stream().map(Artist::getName).collect(Collectors.toList());

    List<TrackResponseDTO> tracks = album.getTracks()
      .stream()
      .map(track -> new TrackResponseDTO(
            track.getId_track(),
            track.getTitle(),
            track.getRelease_date(), 
            track.getOwner().getName() != null ? track.getOwner().getName() : null ,
            albumDTO, 
            artists
          )
       )
      .collect(Collectors.toList());


    return new AlbumResponseDTO(
        album.getId_album(), 
        album.getTitle(), 
        album.getReleaseYear(), 
        album.getOwner().getName() != null ? album.getOwner().getName() : null, 
        album.getArtists(), 
        tracks
        );
  }

  public Album findById(Long id){
    return album_repo.findById(id).orElseThrow(()-> new RuntimeException("no such album"));
  }


  public List<AlbumResponseDTO> getAll(){

    List<Album> albums = album_repo.findAll();

    return albums.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
   
  }

  public AlbumResponseDTO getById(Long Id){

   Album album =  findById(Id);
   return toDTO(album);

  }
  
  public List<AlbumResponseDTO> getByName(String album_name){
  
   List<Album> albums = album_repo.findByTitle(album_name);
   return albums.stream()
     .map(this::toDTO)
     .collect(Collectors.toList());

  }

  public AlbumResponseDTO create(AlbumRequestDTO album , User current_user){
    
    Album new_album = new Album();

    UserArtistLink link = link_repo.findByUser(current_user);

    

    new_album.setTitle(album.getTitle());
    new_album.setReleaseYear(album.getReleaseYear());
    new_album.setOwner(link.getArtist());

    album_repo.save(new_album);
    return toDTO(new_album);
  } 

  public boolean isOwner(User current_user, Album album){
    return link_repo.findByUserAndArtist(current_user, album.getOwner()).map(link -> link.getRole() != ArtistRole.MEMBER).orElse(false);
  }


  public AlbumResponseDTO update( Long id,AlbumRequestDTO album, User current_user){

    Album exist = album_repo.findById(id).orElseThrow(()-> new RuntimeException("no such album // updating stoped."));

    boolean is_owner = isOwner(current_user,exist);
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this album");
    }


    exist.setTitle(album.getTitle());
    exist.setReleaseYear(album.getReleaseYear());

    album_repo.save(exist);
    return toDTO(exist);
  }

  public void delete(Long id,User current_user){

    Album target = album_repo.findById(id).orElseThrow(() -> new RuntimeException("no such album // deleting stoped."));
    
    boolean is_owner = isOwner(current_user, target);
    boolean is_admin = current_user.getRole() == Role.ADMIN;

    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this album");
    }

    album_repo.delete(target);
  }
}
