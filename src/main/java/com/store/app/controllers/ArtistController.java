package com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.DTOs.ArtistDTO.ArtistRequestDTO;
import com.store.app.entities.Artist;
import com.store.app.entities.Track;
import com.store.app.services.ArtistService;

import jakarta.validation.Valid;

/**
 * ArtistController
 */

@RestController
@RequestMapping("/artists")
public class ArtistController {

  @Autowired
  private ArtistService artist_service;


  @GetMapping
  public List<Artist> getALL(){return artist_service.getAll();}

  @GetMapping("/{id}")
  public Artist getById(@PathVariable Long id){return artist_service.getById(id);}

  @GetMapping("/search/{name}")
  public List<Artist> getByName(@PathVariable String name){return artist_service.getByName(name);}

  @GetMapping("/{artistId}/tracks")
  public List<Track> getTracksByArtist(@PathVariable Long artistId){
    return artist_service.getTraksByArtist(artistId);
  }


  @PostMapping
  public Artist create(@Valid @RequestBody ArtistRequestDTO artist){
    return artist_service.create_artist(artist);
  }

  @PostMapping("/{artistId}/track/{trackId}")
  public Artist addTrack(@PathVariable Long artistId, @PathVariable Long trackId) {
    return artist_service.addTrack(artistId, trackId);
  }

  @PostMapping("/{artistId}/album/{albumId}")
  public Artist addAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
    return artist_service.addAlbum(artistId, albumId);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id){ artist_service.delete(id);}

  
}
