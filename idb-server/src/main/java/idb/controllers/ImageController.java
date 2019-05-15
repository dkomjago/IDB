package idb.controllers;

import idb.entity.image.Mod;
import idb.entity.image.payload.ModifiedImage;
import idb.entity.image.payload.Thumbnail;
import idb.entity.image.payload.UploadedImage;

import idb.security.CurrentUser;
import idb.security.UserPrincipal;

import idb.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PutMapping("/image")
    public ResponseEntity<?> uploadImage(@Valid @RequestBody UploadedImage img) {
        return imageService.saveImage(img);
    }

    @DeleteMapping("/image")
    public ResponseEntity<?> deleteImage(@RequestParam("id") Long id) {
        return imageService.deleteImage(id);
    }

    @GetMapping("/image")
    public ModifiedImage getImage(@RequestParam("id") Long id ,@RequestParam("src") boolean needSource) {
        return imageService.getImageById(id, needSource);
    }

    @GetMapping("/image/gallery")
    @PreAuthorize("hasRole('USER')")
    public List<Thumbnail> getImages(@CurrentUser UserPrincipal currentUser) {
        return imageService.getGalleryByUser(currentUser);
    }

    @DeleteMapping("/image/mod")
    public ResponseEntity<?> deleteMod(@RequestParam("id") Long id) {
        return imageService.deleteMod(id);
    }

    @PutMapping("/image/mod")
    public ResponseEntity<?> addMod(@RequestParam("id") Long imageId, @RequestBody Mod mod) {
        return imageService.addMod(imageId, mod);
    }
}
