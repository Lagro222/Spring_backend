package  com.store.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import com.store.app.DTOs.artistDTO.ArtistResponseDTO;
import com.store.app.DTOs.userDTO.UserRequestDTO;
import com.store.app.DTOs.userDTO.UserResponseDTO;
import com.store.app.entities.Artist;
import com.store.app.entities.Playlist;
import com.store.app.entities.Track;
import com.store.app.entities.User;
import com.store.app.enums.Role;
import com.store.app.repositories.PlaylistRepository;
import com.store.app.repositories.UserRepository;

// import jakarta.transaction.Transactional;

/**
 * UserService
 */
@Service
public class UserService implements UserDetailsService  {

  @Autowired
  private UserRepository user_repo;

  @Autowired
  private TrackService track_service;

  @Autowired
  private ArtistService artist_service;

   @Autowired
  private PlaylistRepository playlist_repo;
  

  public UserResponseDTO toDto(User user){
    return new UserResponseDTO(
        user.getId_user(),
        user.getName(),
        user.getFirstname(),
        user.getFollowed_Artists()
        ); 
  } 


  //basic get functions
  public List<User> getAll(){return user_repo.findAll();}
  public List<UserResponseDTO> SearchAll(String name){
    List<User> users = user_repo.findByName(name);
    return users.stream().map(this::toDto).collect(Collectors.toList());

  }
  public List<UserResponseDTO> searchAritsts(String name){
    List<User> users = user_repo.findByName(name);
    return users.stream()
      .filter(user -> user.getRole() == Role.ARTIST)
      .map(this::toDto)
      .collect(Collectors.toList());
  }
  public User getById(Long id){return user_repo.findById(id).orElseThrow(()-> new RuntimeException("no such user"));}
  public Playlist getPlayListByID(Long playlistId){
    Playlist playlist = playlist_repo.findById(playlistId).orElseThrow(() -> new RuntimeException("EROR: playlist not found!!"));
    return playlist;
  }

  public User create_user(User new_user){
    return user_repo.save(new_user);
  }
  public User update_user(Long user_id, UserRequestDTO updating, User current_user){
    
    boolean is_owner = current_user.getId_user().equals(user_id);
    boolean is_admin = current_user.getRole() == Role.ADMIN;
    
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
    Track target_track = track_service.findById(trackId);

    target_user.getLiked().add(target_track);

    return user_repo.save(target_user);

  }

  public User unlikedTrack(Long trackId, Long userId){

    User target_user = getById(userId);
    Track target_track = track_service.findById(trackId);

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

  @Transactional
  public User follow_playlist(User current_user, Long playlistId){
    
    User user = getById(current_user.getId_user());

    Playlist playlist = getPlayListByID(playlistId);

    if (playlist.getFollowers().contains(user)) {
     System.out.println("playlist already followed");
    }else {
      playlist.getFollowers().add(user);
    }
 

    System.out.println("playlist followers Number" + playlist.getFollowers().size());
    playlist_repo.save(playlist);

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


  @Override
  public UserDetails loadUserByUsername(String email){
    return user_repo.findByEmail(email).orElseThrow(() -> new RuntimeException("no such user"));
  }

}
