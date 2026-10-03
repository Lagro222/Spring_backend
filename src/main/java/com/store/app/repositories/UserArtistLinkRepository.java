package  com.store.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.UserArtistLink;

@Repository
public interface UserArtistLinkRepository extends JpaRepository<UserArtistLink,Long>{

  
}
