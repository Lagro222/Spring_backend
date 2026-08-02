package com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.entities.Track;
import com.store.app.services.TrackService;

/**
 * TrackController
 */

@RestController
@RequestMapping("/tracks")
public class TrackController {

  @Autowired
  private TrackService track_service;

  @GetMapping
  public List<Track> getAll(){return track_service.getAll();}

  @GetMapping("/search")
  public List<Track> getByTitle(@RequestParam(required = false) Long id,@RequestParam(required = false) String title){
    if (id != null) return List.of(track_service.getById(id)); 
    if (title != null) return track_service.getByTitle(title);

    return track_service.getAll();
  }

  @PutMapping("/update/{id}")
  public Track update(@PathVariable Long id, @RequestBody Track updating){return track_service.update_track(id, updating);}

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id ){
    track_service.delete_track(id);
  }
}
