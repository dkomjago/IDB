package idb.service;

import idb.entity.image.payload.UploadedImage;
import idb.entity.image.Image;
import idb.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ImageService{
    @Autowired
    ImageRepository imageRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public Image getImageById(Long imageId) {
        return imageRepository.getOne(imageId);
    }

    public void saveImage(UploadedImage uploaded) {

        Image image = new Image();
        imageRepository.save(image);
        image.setCreatedBy(uploaded.getUserId());
        image.setFileType(uploaded.getFileType());
        image.setMods(uploaded.getMods().toArray(new String[0]));

        image.setPath("storage/" + uploaded.getUserId() + "/" + image.getImageId() + "." + uploaded.getFileType());

        File directory = new File("storage/" + uploaded.getUserId().toString());
        if (!directory.exists()){
            directory.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(image.getPath(),false)) {
            fos.write(uploaded.getImage().getBytes());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List< Image > getAllImagesByUser() {
        return imageRepository.findAll();
    }
}
