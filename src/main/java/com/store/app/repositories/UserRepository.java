package com.store.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.User;

/**
 * UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  List<User> findByName(String name);
  
}
