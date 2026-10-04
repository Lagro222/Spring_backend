package com.store.app.repositories;

import java.util.List;
// import java.util.Optional;
import java.util.Optional;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Artist;
// import com.store.app.entities.User;

/**
 * ArtistRepository
 */

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {

  Optional<Artist> findByName(String name);
  List<Artist> findByNameContaining(String name);
}
