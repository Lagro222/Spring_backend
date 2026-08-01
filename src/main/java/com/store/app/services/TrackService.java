package com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.Track;
import com.store.app.repositories.TrackRepository;

/**
 * TrackService
 */

@Service
public class TrackService {

  @Autowired
  public TrackRepository track_repository;

  public List<Track> getAll(){return track_repository.findAll();}
  public List<Track> getByTitle(String title){return track_repository.findByTitle(title);}
  public Track getById(Long id){return track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track"));}


}
