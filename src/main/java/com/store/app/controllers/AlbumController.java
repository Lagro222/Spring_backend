package  com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.services.AlbumService;
import com.store.app.entities.Album;
/**
 * AlbumController
 */
@RestController
@RequestMapping("/albums")
public class AlbumController {

  @Autowired
  private AlbumService album_service;

  @GetMapping
  public List<Album> getAll(){
    return album_service.getAll();
  }

  @GetMapping("/{id}")
  public Album getById(@PathVariable Long id){return album_service.getById(id);}

  @GetMapping("/{title}")
  public List<Album> getByTitle(@PathVariable String title){return album_service.getByName(title);}
 
  @PostMapping
  public Album create_Album(@RequestBody Album new_album){return album_service.create(new_album);}

  @PostMapping("/update")
  public Album update_Album(@RequestBody Album new_album){
    return   album_service.update(new_album);
  }
}
