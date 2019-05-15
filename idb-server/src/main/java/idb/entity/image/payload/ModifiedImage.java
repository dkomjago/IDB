package idb.entity.image.payload;

import idb.entity.image.Mod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO for sending image mods and data without the image itself
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifiedImage {
    private String image;
    private String fileType;
    private Set<Mod> mods;
}
