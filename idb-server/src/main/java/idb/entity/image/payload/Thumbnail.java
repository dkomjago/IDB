package idb.entity.image.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for sending image thumbnail
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail {
    /// Thumbnail base64 string
    private String thumbnail;

    /// Image base64 string
    private String src;

    /// Image id in database
    private Long realId;

    private int thumbnailWidth = WIDTH;
    private int thumbnailHeight = HEIGHT;

    /// Default size values
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;
}
