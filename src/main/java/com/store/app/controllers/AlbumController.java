package  com.store.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.services.AlbumService;

import jakarta.validation.Valid;

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

  // @GetMapping("/{id}")
  // public Album getById(@PathVariable Long id){return album_service.getById(id);}
  
  @GetMapping("/search")
  public List<Album> getByTitle(@RequestParam(required = false) Long id,@RequestParam(required = false) String title){
    if (id != null) return List.of(album_service.getById(id)); 
    if (title != null) return album_service.getByName(title);

    return album_service.getAll();
  }
 
  @PostMapping
  public Album create_Album(@Valid @RequestBody Album new_album){return album_service.create(new_album);}

  @PutMapping("/{id}")
  public Album update_Album(@PathVariable Long id ,@Valid @RequestBody Album new_album){
    return   album_service.update(id,new_album);
  }
}
