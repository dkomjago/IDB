package idb.controllers;

import idb.entity.image.payload.UploadedImage;
import idb.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/image/upload")
    public void uploadImage(@RequestBody UploadedImage img) {
        imageService.saveImage(img);
    }
}
