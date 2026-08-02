package  com.store.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.services.UserService;

/**
 * UserController
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService user_service;

  @GetMapping
  public List<User> getAll(){return user_service.getAll();}

  @GetMapping("/search/{name}")
  public List<User> getByName(@PathVariable String name){ return user_service.getByName(name;}

  @GetMapping("/{id}")
  public User getById(Long id){return user_service.getById(id);}


}
