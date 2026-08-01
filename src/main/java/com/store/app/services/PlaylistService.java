package com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.Playlist;
import com.store.app.repositories.PlaylistRepository;

/**
 * PlaylistService
 */
@Service
public class PlaylistService {

  @Autowired
  private PlaylistRepository playlist_repo;

  public List<Playlist> getAll(){return playlist_repo.findAll();}
  public Playlist getById(Long id){return playlist_repo.findById(id).orElseThrow(() -> new RuntimeException("no such playlist"));}
  public List<Playlist> getByTitle(String title){return playlist_repo.findByName(title);}

  public Playlist create_playlist(Playlist new_playlist){
    return playlist_repo.save(new_playlist);
  }

  public Playlist update_playlist(Long id,Playlist updated){
    Playlist target_playlist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist !! updating failed"))
  }

  public void delete_playlist(Long id){
    Playlist exist = playlist_repo.findById(id).orElseThrow(()-> new RuntimeException("no such playlist ! deleting failed"));
    playlist_repo.delete(exist);
  }
}
