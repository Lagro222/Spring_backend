package com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Track;

/**
 * TrackRepository
 */
@Repository
public interface TrackRepository extends JpaRepository<Track,Long> {

}

