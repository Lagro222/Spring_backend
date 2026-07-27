package com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.Album;
import com.store.app.repositories.AlbumRepository;

/**
 * AlbumService
 */

@Service
public class AlbumService {

  @Autowired
  private AlbumRepository album_repo;
 
  public List<Album> getAll(){return album_repo.findAll(); }

  public Album getById(Long Id){return album_repo.findById(Id).orElseThrow(() -> new RuntimeException("no such album"));}
  
  public List<Album> getByName(String album_name){return album_repo.findByTitle(album_name);} 
  
}
