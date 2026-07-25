package com.store.app.repositories;

import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Album;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
  List<Album> findByTitle(String name);   

}
