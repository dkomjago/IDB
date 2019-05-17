package idb.service;

import idb.entity.image.Mod;
import idb.entity.image.payload.ModifiedImage;
import idb.entity.image.payload.Thumbnail;
import idb.entity.image.payload.UploadedImage;
import idb.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImageService {
    ModifiedImage getImageById(Long imageId, boolean needSource);
    List<Thumbnail> getGalleryByUser(UserPrincipal user);
    ResponseEntity<?> saveImage(UploadedImage uploaded);
    ResponseEntity<?> deleteImage(Long id);
    ResponseEntity<?> addMod(Long imageId, Mod mod);
    ResponseEntity<?> deleteMod(Long id);
}
