package com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.PlaylistTrack;

/**
 * PlaylistTrackRepository
 */
@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack,Long> {

}
