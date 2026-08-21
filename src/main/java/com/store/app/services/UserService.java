package  com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.Artist;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.repositories.UserRepository;

/**
 * UserService
 */
@Service
public class UserService {

  @Autowired
  private UserRepository user_repo;

  @Autowired
  private TrackService track_service;

  @Autowired
  private ArtistService artist_service;
  
  public List<User> getAll(){return user_repo.findAll();}
  public List<User> getByName(String name){return user_repo.findByName(name);}
  public User getById(Long id){return user_repo.findById(id).orElseThrow(()-> new RuntimeException("no such user"));}

  public User create_user(User new_user){
    return user_repo.save(new_user);
  }
  public User update_user(Long user_id, User updating){
    
    User target_user = getById(user_id);
    target_user.setName(updating.getName());
    target_user.setEmail(updating.getEmail());
    target_user.setFirstname(updating.getFirstname());
    
    return user_repo.save(target_user);

  }

  public void delete_user(Long id){
    User target_user = getById(id);
    user_repo.delete(target_user);
  }

  public User likedTrack(Long trackId, Long userId){

    User target_user = getById(userId);
    Track target_track = track_service.getById(trackId);

    target_user.getLiked().add(target_track);

    return user_repo.save(target_user);

  }

  public User unlikedTrack(Long trackId, Long userId){

    User target_user = getById(userId);
    Track target_track = track_service.getById(trackId);

    target_user.getLiked().remove(target_track);

    return user_repo.save(target_user);

  }

  public List<Track> getLikedTracks(Long userId){
    User target_user = getById(userId);
    return target_user.getLiked();
  }

  public User follow_artist(Long userId, Long artistId){
    User user = getById(userId);
    Artist artist = artist_service.getById(artistId);

    user.getFollowed_Artists().add(artist);
    return user_repo.save(user);
  }

  public User unfollow_artist(Long userId, Long artistId){
    User user = getById(userId);
    Artist artist = artist_service.getById(artistId);

    user.getFollowed_Artists().remove(artist);
    return user_repo.save(user);
  }


}
