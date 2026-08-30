package com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.store.app.entities.Playlist;
// import com.store.app.enums.PlaylistType;
import com.store.app.services.PlaylistService;

/**
 * PlaylistController
 */
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

  @Autowired
  private PlaylistService playlist_service;


  //getMapping :
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

  //postmapping
  @PostMapping
  public Playlist create_playlist(@RequestParam(required = false) Long userId, @Valid @RequestBody Playlist new_playlist){
    return playlist_service.create_playlist(userId,new_playlist);
  }

  @PostMapping("/{playlistId}/track/{trackId}")
  public Playlist addTrack(@PathVariable Long playlistId,@PathVariable Long trackId,@RequestParam Long userId){
    return playlist_service.add_track(playlistId, trackId, userId);
  }

}
