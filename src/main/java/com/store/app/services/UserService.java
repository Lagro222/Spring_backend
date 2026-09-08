package  com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.DTOs.UserRequestDTO;
import com.store.app.entities.Artist;
import com.store.app.entities.Playlist;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.Role;
import com.store.app.repositories.PlaylistRepository;
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

  @Autowired
  private PlaylistRepository playlist_repo;
  
  //basic get functions
  public List<User> getAll(){return user_repo.findAll();}
  public List<User> getByName(String name){return user_repo.findByName(name);}
  public User getById(Long id){return user_repo.findById(id).orElseThrow(()-> new RuntimeException("no such user"));}
  public Playlist getPlayListByID(Long playlistId){
    Playlist playlist = playlist_repo.findById(playlistId).orElseThrow(() -> new RuntimeException("EROR: playlist not found!!"));
    return playlist;
  }

  public User create_user(UserRequestDTO new_user){
    
    User user = new User();
    user.setName(new_user.getName());
    user.setEmail(new_user.getEmail());
    user.setFirstname(new_user.getFirstname());

    return user_repo.save(user);
  }
  public User update_user(Long user_id, UserRequestDTO updating, User current_user){
    
    boolean is_owner = current_user.getId_user().equals(user_id);
    boolean is_admin = current_user.getRole() == Role.AADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to update this user");
    }

    User target_user = getById(user_id);
    target_user.setName(updating.getName());
    target_user.setEmail(updating.getEmail());
    target_user.setFirstname(updating.getFirstname());
    
    return user_repo.save(target_user);

  }

  public void delete_user(Long id, User current_user){
    User target_user = getById(id);

    boolean is_owner = target_user.getId_user().equals(current_user.getId_user());
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
    if(!is_owner && !is_admin){
      throw new RuntimeException("you are not authorized to delete this user");
    }

    user_repo.delete(target_user);
  }

  //like/unlike feature
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

  //follow/unfollow features
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

  public User follow_playlist(Long userID, Long playlistId){
    
    User user = getById(userID);
    Playlist playlist = getPlayListByID(playlistId);

    user.getFollowed_playlists().add(playlist);
    return user_repo.save(user);
  }

  public User unfollow_playlist(Long userId,Long playlistId){
    
    User user = getById(userId);
    Playlist playlist = getPlayListByID(playlistId);

    user.getFollowed_playlists().remove(playlist);
    return user_repo.save(user);
  }


  public List<Artist> getFollowed_artist(Long userId){
    User user = getById(userId);
    return user.getFollowed_Artists();
  }

  public List<Playlist> getFollowed_playlists(Long userId){
    User user = getById(userId);
    return user.getFollowed_playlists();
  }

}
