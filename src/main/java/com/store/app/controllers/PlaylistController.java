package com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.entities.Playlist;
import com.store.app.services.PlaylistService;

/**
 * PlaylistController
 */
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

  @Autowired
  private PlaylistService playlist_service;

  @GetMapping
  public List<Playlist> getAll(){
    return playlist_service.getAll();
  }

  @GetMapping("/{id}")
  public Playlist getById(@PathVariable Long id){
    return playlist_service.getById(id);
  }

  @GetMapping("/search/{name}")
  public List<Playlist> getByName(@PathVariable String name){
    return playlist_service.findByName(name);
  }

  
}
