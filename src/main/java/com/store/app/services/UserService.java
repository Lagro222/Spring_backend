package  com.store.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.entities.User;
import com.store.app.repositories.UserRepository;

/**
 * UserService
 */
@Service
public class UserService {

  @Autowired
  private UserRepository user_repo;

  public List<User> getAll(){return user_repo.findAll();}
  public List<User> getByName(String name){return user_repo.findByName(name);}
  public User getById(Long id){return user_repo.findById(id).orElseThrow(()-> new RuntimeException("no such user"));}

}
