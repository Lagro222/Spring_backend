package  com.store.app.DTOs.ArtistDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ArtistRequestDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRequestDTO {
  
  @NotBlank(message = "Please enter a valid name")
  private String name;
  
  @NotBlank(message = "Please enter a valid genre")
  private String genre;
  
  @NotBlank(message = "Please enter a valid country")
  private String country;
}
