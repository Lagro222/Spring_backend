package  com.store.app.DTOs.albumDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelperAlbumDTO {

  Long id;
  String title;
  Integer realeaseYear;
  String owner_name;

}
