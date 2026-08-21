package  com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.services.UserService;
import com.store.app.entities.Artist;
import com.store.app.entities.Playlist;
import com.store.app.entities.Track;
import com.store.app.entities.User;

/**
 * UserController
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService user_service;

  //GET
  @GetMapping
  public List<User> getAll(){return user_service.getAll();}

  @GetMapping("/search/{name}")
  public List<User> getByName(@PathVariable String name){ return user_service.getByName(name);}

  @GetMapping("/{id}")
  public User getById(Long id){return user_service.getById(id);}

  @GetMapping("/{id}/liked")
  public List<Track> getLikedTracks(@PathVariable Long id){
    return user_service.getLikedTracks(id);
  }

  @GetMapping("/{userId}/followed_artists")
  public List<Artist> getFollowed_artists(@PathVariable Long userId){
    return user_service.getFollowed_artist(userId);
  }
  
  @GetMapping("/{userId}/followed_playlists")
  public List<Playlist> getFollowed_playlists(@PathVariable Long userId){
    return user_service.getFollowed_playlists(userId);
  }

  //POST 
  @PostMapping
    public User create_user(@RequestBody User new_user){
    return user_service.create_user(new_user);
  }

  @PostMapping("/{userId}/like/{trackId}")
  public User likedTrack(@PathVariable Long trackId,@PathVariable Long userId){
    return user_service.likedTrack(trackId, userId);
  }

  @PostMapping("/{userId}/follow/artist/{artistId}")
  public User follow_artist(@PathVariable Long userId,@PathVariable Long artistId){
    return user_service.follow_artist(userId, artistId);
  }

  @DeleteMapping("/{userId}/follow/artist/{artistId}")
  public User unfollow_artist(@PathVariable Long userId,@PathVariable Long artistId){
    return user_service.unfollow_artist(userId, artistId);
  }

}
