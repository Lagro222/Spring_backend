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

  public Track create_track(Track new_track){return track_repository.save(new_track);}
  public Track update_track(Long id , Track updating){
    Track exist = track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track.. updating failed !!"));
    exist.setAlbum(updating.getAlbum());
    exist.setAlbum(updating.getAlbum());;
    exist.setPlaylists(updating.getPlaylists());
    exist.setFile_path(updating.getFile_path());
    exist.setRealease_date(updating.getRealease_date());

    exist.setTitle(updating.getTitle());

    return track_repository.save(exist);
  }
  public void delete_track(Long id){
    
    Track exist = track_repository.findById(id).orElseThrow(()-> new RuntimeException("no such track.. deleting failed !!"));
    track_repository.delete(exist);

  }
}
