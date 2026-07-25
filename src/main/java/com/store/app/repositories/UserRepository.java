package com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.User;

/**
 * UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  
}
