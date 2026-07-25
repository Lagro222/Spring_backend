package com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Artist;

/**
 * ArtistRepository
 */

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {

  
}
