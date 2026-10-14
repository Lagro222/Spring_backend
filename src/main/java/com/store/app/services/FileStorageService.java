package com.store.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
 
  @Value("${app.upload.dir}")
  private String uploadDir;

  public String saveAudio(MultipartFile file, String artistName, String trackTile){
   
    try{

      String dir_path = uploadDir + "/" + artistName + "/" + trackTile;
      Files.createDirectories(Paths.get(dir_path));

      String filename = trackTile.replaceAll(" ", "_") + ".mp3";
      String file_path = dir_path + "/" + filename;

      Files.copy(file.getInputStream(),Paths.get(file_path),StandardCopyOption.REPLACE_EXISTING);

      return file_path;

    }catch(IOException ex){
      throw new RuntimeException("Failed to save this file " + ex.getMessage());
    }
    
  }

}
