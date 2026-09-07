package com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.AlbumRequestDTO;
import com.store.app.entities.Album;
import com.store.app.entities.User;
import com.store.app.enums.Role;
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

  public Album create(AlbumRequestDTO album , User owner){
    
    Album new_album = new Album();
    new_album.setTitle(album.getTitle());
    new_album.setReleaseYear(album.getReleaseYear());
    new_album.setOwner(owner);

    return album_repo.save(new_album);
  } 

  public Album update( Long id,AlbumRequestDTO album, User current_user){

    Album exist = album_repo.findById(id).orElseThrow(()-> new RuntimeException("no such album // updating stoped."));

    boolean is_owner = exist.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this album");
    }


    exist.setTitle(album.getTitle());
    exist.setReleaseYear(album.getReleaseYear());

    return album_repo.save(exist);
  }

  public void delete(Long id,User current_user){

    Album target = album_repo.findById(id).orElseThrow(() -> new RuntimeException("no such album // deleting stoped."));
    
    boolean is_owner = target.getOwner().getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;

    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this album");
    }

    album_repo.delete(target);
  }
}
