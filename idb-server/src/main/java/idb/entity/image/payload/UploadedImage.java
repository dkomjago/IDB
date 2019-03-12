package idb.entity.image.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadedImage {
    private String image;
    private String fileType;
    private ArrayList<String> mods;
    private Long userId;
    private String username;
}
