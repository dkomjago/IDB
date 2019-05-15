package idb.entity.image.payload;

import idb.utilities.payload.ApiResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for responding to an image upload
 */
@Getter
@Setter
public class UploadedImageResponse extends ApiResponse {
    private Long imageId;
    private boolean exists;

    public UploadedImageResponse(Long imageId, boolean exists){
        this.imageId = imageId;
        this.success = true;
        this.message = exists? "Image already present, added as modification.": "Upload successful!";
        this.exists = exists;
    }
}
