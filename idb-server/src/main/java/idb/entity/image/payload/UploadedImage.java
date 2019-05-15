package idb.entity.image.payload;

import idb.entity.image.Mod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO for receiving uploaded image
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadedImage {
    private String image;
    private String fileType;
    private Mod mod;
    private Long userId;
    private String username;
}
