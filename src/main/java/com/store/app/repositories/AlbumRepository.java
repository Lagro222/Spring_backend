package com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Album;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
}
