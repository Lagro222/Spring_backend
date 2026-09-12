package com.store.app.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.PlaylistRequestDTO;
import com.store.app.entities.Playlist;
import com.store.app.entities.PlaylistTrack;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.PlaylistType;
import com.store.app.enums.Role;
import com.store.app.repositories.PlaylistRepository;
import com.store.app.repositories.PlaylistTrackRepository;

/**
 * PlaylistService
 */
@Service
public class PlaylistService {

  @Autowired
  private PlaylistRepository playlist_repo;

  @Autowired
  private TrackService track_service;

  @Autowired
  private UserService userService;

  @Autowired
  private PlaylistTrackRepository playlist_track_repo;

  public List<Playlist> getAll(){return playlist_repo.findAll();}
  public Playlist getById(Long id){return playlist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such playlist"));}
  public List<Playlist> getByTitle(String title){return playlist_repo.findByName(title);}

  public Playlist create_playlist(Long userId,PlaylistRequestDTO new_playlist , User current_user){
    
    Playlist playlist = new Playlist();

    if(userId != null){
      User user = userService.getById(userId);
      playlist.setUser(user);
      playlist.setType(PlaylistType.USER);
    }else {
      playlist.setType(PlaylistType.GLOBAL);
    }

    playlist.setName(new_playlist.getName());
    playlist.setCollaborative(new_playlist.getIsCollaborative());
    playlist.setUser(current_user);
    
    return playlist_repo.save(playlist);
  }

  public Playlist update_playlist(Long id,PlaylistRequestDTO updated, User current_user){
    
    Playlist target_playlist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist !! updating failed"));
    
    boolean is_owner = target_playlist.getUser().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this playlist");
    }


    target_playlist.setName(updated.getName());
    target_playlist.setType(updated.getPlaylistType()) ;
    target_playlist.setCollaborative(updated.getIsCollaborative());
   
    return playlist_repo.save(target_playlist);
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
    public Playlist add_track(Long playlistId,Long trackId, Long userId){

      Playlist target_palylist = getById(playlistId);
    
      if(!target_palylist.isCollaborative() && !target_palylist.getUser().getId_user().equals(userId)){
        throw new RuntimeException("you can't add track in this playlist is not collaborative");
      }

      Track target_track = track_service.getById(trackId);
      User target_user = userService.getById(userId);

      PlaylistTrack new_playlistTrack = new PlaylistTrack();
      new_playlistTrack.setPlaylist(target_palylist);
      new_playlistTrack.setTrack(target_track);
      new_playlistTrack.setAddedBy(target_user);
      new_playlistTrack.setAddedAt(LocalDateTime.now());
      new_playlistTrack.setPostion(target_palylist.getTracks().size() + 1);

      playlist_track_repo.save(new_playlistTrack);

      return target_palylist;
    }

  //searching functions
  public List<Playlist> findByName(String name){ return playlist_repo.findByName(name);}
}
