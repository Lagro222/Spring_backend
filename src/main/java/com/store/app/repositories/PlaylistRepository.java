package com.store.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Playlist;

/**
 * PlaylistRepository
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Long>{

  List<Playlist> findByName(String name);
  
}
