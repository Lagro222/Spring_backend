package com.store.app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.playlistDTO.PlaylistRequestDTO;
import com.store.app.DTOs.playlistDTO.PlaylistResponseDTO;
import com.store.app.DTOs.tracksDTO.HelperTrackDTO;
// import com.store.app.DTOs.tracksDTO.TrackResponseDTO;
import com.store.app.entities.Playlist;
import com.store.app.entities.PlaylistTrack;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.PlaylistType;
import com.store.app.enums.Role;
import com.store.app.repositories.PlaylistRepository;
import com.store.app.repositories.PlaylistTrackRepository;
// import com.store.app.repositories.UserRepository;

import jakarta.transaction.Transactional;

/**
 * PlaylistService
 */
@Service
public class PlaylistService {

  @Autowired
  private PlaylistRepository playlist_repo;

  @Autowired
  private TrackService track_service;

  // @Autowired
  // private UserRepository user_repo;
  // private UserService userService;

  @Autowired
  private PlaylistTrackRepository playlist_track_repo;

  public List<PlaylistResponseDTO> getAll(){
     
    List<Playlist> playlists = playlist_repo.findAll();
    return playlists.stream().map(this::toDTO).collect(Collectors.toList());
  }
  public PlaylistResponseDTO getById(Long id){
    
    Playlist playlist =  playlist_repo
    .findById(id)
    .orElseThrow(() -> new RuntimeException("no such playlist"));

    return toDTO(playlist);

  }
  public List<PlaylistResponseDTO> getByTitle(String title){
    List<Playlist> playlists = playlist_repo.findByName(title);
    return playlists.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Transactional
  public PlaylistResponseDTO toDTO(Playlist playlist){
    List<HelperTrackDTO> tracks = playlist.getTracks()
      .stream()
      .map(playlistTrack -> new HelperTrackDTO(
            playlistTrack.getTrack().getTitle(),
            playlistTrack.getTrack().getArtists().isEmpty() ? 
            playlistTrack.getTrack().getOwner().getName() : playlistTrack.getTrack().getArtists().get(0).getName(),
            playlistTrack.getTrack().getAlbum() != null ? playlistTrack.getTrack().getAlbum().getTitle() : null
            ) )
      .collect(Collectors.toList());

    Integer likes = playlist.getType() == PlaylistType.GLOBAL ? playlist.getFollowers().size() : null;

    return new PlaylistResponseDTO(
        playlist.getId_playlist(),
        playlist.getName(),
        playlist.getUser().getName(),
        playlist.getType(), 
        playlist.isCollaborative(), 
        tracks,
        likes
        );
  }


  public PlaylistResponseDTO create_playlist(Long userId,PlaylistRequestDTO new_playlist , User current_user){
    
    Playlist playlist = new Playlist();
    
    // if(userId != null){
    //   User user = userService.getById(userId);
    //   playlist.setUser(user);
    //   playlist.setType(PlaylistType.USER);
    // }else {
    //   playlist.setType(PlaylistType.GLOBAL);
    // }
    //

    playlist.setUser(current_user);
    playlist.setType(new_playlist.getPlaylistType());
    playlist.setName(new_playlist.getName());
    playlist.setCollaborative(new_playlist.getIsCollaborative());
    playlist.setUser(current_user);
    
    playlist_repo.save(playlist);
    System.out.println("playlist owner" + playlist.getUser().getId_user());

    return toDTO(playlist);
  }

  public PlaylistResponseDTO update_playlist(Long id,PlaylistRequestDTO updated, User current_user){
    
    Playlist target_playlist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist !! updating failed"));
    
    boolean is_owner = target_playlist.getUser().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this playlist");
    }


    target_playlist.setName(updated.getName());
    target_playlist.setType(updated.getPlaylistType()) ;
    target_playlist.setCollaborative(updated.getIsCollaborative());
   
    playlist_repo.save(target_playlist);

    return toDTO(target_playlist);
  }

  public void delete_playlist(Long id, User current_user){

    Playlist exist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist ! deleting failed"));
    
    boolean is_owner = exist.getUser().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this playlist");
    }

    playlist_repo.delete(exist);
  }

  //relations functions
  // public Playlist add_Track(Long playlistId,Long trackId){
  //   Track target_track = track_service.getById(trackId);
  //   Playlist target_playlist = getById(playlistId);
  //   target_playlist.getPlaylist_tracks().add(target_track);
  //
  //   return playlist_repo.save(target_playlist);
  // }
    @Transactional
    public PlaylistResponseDTO add_track(Long playlistId,Long trackId, User current_user){

      // System.out.println("user id" + userId);
      Playlist target_palylist =  playlist_repo
        .findById(playlistId)
        .orElseThrow(() -> new RuntimeException("no such playlist"));

      System.out.println("playlist owner:" + target_palylist.getUser().getId_user());
      // System.out.println("current user:" + userId);
    
      if( target_palylist.getUser().getId_user() != current_user.getId_user() && target_palylist.isCollaborative() == false){
        throw new RuntimeException("you can't add track in this playlist is not collaborative");
      }

      Track target_track = track_service.findById(trackId);
      // target_palylist.getTracks().add(track_service.toDTO(target_track));
      // User target_user = user_repo.findById(current_user.getId_user()).orElseThrow(() -> new RuntimeException("no such user")); 

      PlaylistTrack new_playlistTrack = new PlaylistTrack();
      new_playlistTrack.setPlaylist(target_palylist);
      new_playlistTrack.setTrack(target_track);
      new_playlistTrack.setAddedBy(current_user);
      new_playlistTrack.setAddedAt(LocalDateTime.now());
      new_playlistTrack.setPostion(target_palylist.getTracks().size() + 1);

      playlist_track_repo.save(new_playlistTrack);

      PlaylistResponseDTO playlist_response = toDTO(target_palylist);
      playlist_response.getTracks().add(
          new HelperTrackDTO(
            target_track.getTitle(), 
            target_track.getOwner() != null ? target_track.getOwner().getName() : null,
            target_track.getAlbum() != null ? target_track.getAlbum().getTitle(): null
            ));

      return playlist_response;
    }

  
}
