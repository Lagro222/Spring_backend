package  com.store.app.repositories;

import java.util.Optional;

// import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.entities.Artist;
import com.store.app.entities.User;
import com.store.app.entities.UserArtistLink;

@Repository
public interface UserArtistLinkRepository extends JpaRepository<UserArtistLink,Long>{

  UserArtistLink findByUser(User user);
  Optional<UserArtistLink> findByUserAndArtist(User user, Artist artist);
  
}
