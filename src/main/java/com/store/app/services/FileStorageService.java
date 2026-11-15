package com.store.app.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
 
  @Value("${app.upload.dir}")
  private String uploadDir;


  public String saveAudio(MultipartFile file , String artist_name , String title){

    if(!file.getOriginalFilename().contains(".mp3")){
        throw new IllegalArgumentException("file must be mp3 format");
    }  

    if(file.isEmpty()){
        throw new IllegalArgumentException("file is is empty");
    }
 
    String safeTitle = title.replaceAll("[^a-zA-Z0-9]", "_") + ".mp3";
    Path dir = Paths.get(uploadDir,artist_name);
    Path destination = dir.resolve(safeTitle);
  
     try {
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), destination,StandardCopyOption.REPLACE_EXISTING);
          
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("uploading failed !!");
      }

     return destination.toString();

  }

  public Resource LoadAudio(String file_path){
      try {
        Resource resource = new UrlResource(Paths.get(file_path).toUri());
        if (!resource.exists() || !resource.isReadable()) {
          throw new RuntimeException("audio file not found ");
          
        }
        return resource;
      } catch (MalformedURLException ex) {
          throw new RuntimeException("invalid file path",ex);
      }
  } 

}
