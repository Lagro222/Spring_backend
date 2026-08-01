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

  public Album create(Album album){
    return album_repo.save(album);
  } 

  public Album update( Long id,Album album){

    Album exist = album_repo.findById(id).orElseThrow(()-> new RuntimeException("no such album // updating stoped."));

    exist.setArtists(album.getArtists());
    exist.setReleaseYear(album.getReleaseYear());
    exist.setTitle(album.getTitle());
    exist.setTracks(album.getTracks());

    return album_repo.save(exist);
  }

  public void delete(Long id){

    Album target = album_repo.findById(id).orElseThrow(() -> new RuntimeException("no such album // deleting stoped."));
    album_repo.delete(target);
  }
}
