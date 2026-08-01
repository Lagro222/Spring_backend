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

}
