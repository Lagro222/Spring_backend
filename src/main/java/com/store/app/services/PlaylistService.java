package com.store.app.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.Playlist;
import com.store.app.entities.PlaylistTrack;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.PlaylistType;
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

  public Playlist create_playlist(Long userId,Playlist new_playlist){
    
    if(userId != null){
      User user = userService.getById(userId);
      new_playlist.setUser(user);
      new_playlist.setType(PlaylistType.USER);
    }else {
      new_playlist.setType(PlaylistType.GLOBAL);
    }
    return playlist_repo.save(new_playlist);
  }

  public Playlist update_playlist(Long id,Playlist updated){
    Playlist target_playlist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist !! updating failed"));
    target_playlist.setName(updated.getName());
    target_playlist.setPlaylist_tracks(updated.getPlaylist_tracks());
    target_playlist.setType(updated.getType());
    target_playlist.setUser(updated.getUser());
    
    return playlist_repo.save(target_playlist);
  }

  public void delete_playlist(Long id){
    Playlist exist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist ! deleting failed"));
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
