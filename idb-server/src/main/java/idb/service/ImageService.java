package idb.service;

import idb.entity.image.Mod;
import idb.entity.image.payload.ModifiedImage;
import idb.entity.image.payload.Thumbnail;
import idb.entity.image.payload.UploadedImage;
import idb.entity.image.Image;
import idb.entity.image.payload.UploadedImageResponse;
import idb.repository.ImageRepository;
import idb.repository.ModRepository;
import idb.security.UserPrincipal;
import idb.utilities.payload.ApiResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageService{

    private ImageRepository imageRepository;
    private ModRepository modRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    public ImageService(ImageRepository imageRepository, ModRepository modRepository){
        this.imageRepository = imageRepository;
        this.modRepository = modRepository;
    }

    public ModifiedImage getImageById(Long imageId, boolean needSource) {
        Image img = imageRepository.getOne(imageId);
        ModifiedImage modImg = new ModifiedImage();

        try {
            if(needSource) {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(img.getPath()));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                modImg.setImage("data:image/jpg;base64," + encodedString);
            }
            modImg.setFileType(img.getFileType());
            modImg.setMods(modRepository.findAllByImageId(img.getId()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return modImg;
    }

    public ResponseEntity<?> saveImage(UploadedImage uploaded) {

        Image img = new Image();
        img.setCreatedBy(uploaded.getUserId());
        img.setFileType(uploaded.getFileType());

        String sha256hex = DigestUtils.sha256Hex(uploaded.getImage());

        img.setPath("storage/" + uploaded.getUserId() + "/" + sha256hex);
        Mod mod = uploaded.getMod();

        boolean exists = imageRepository.existsByPath(img.getPath());
        if(exists){
            img = imageRepository.getByPath(img.getPath());
            mod.setImage(img);
        }

        File directory = new File("storage/" + uploaded.getUserId().toString());
        if (!directory.exists()){
            directory.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(img.getPath(),false)) {
            byte[] data = Base64.getDecoder().decode(uploaded.getImage());
            fos.write(data);
            imageRepository.save(img);
            mod.setImage(img);
            modRepository.save(mod);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "Upload failed!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new UploadedImageResponse(img.getId(), exists),
                HttpStatus.CREATED);
    }

    public List<Thumbnail> getGalleryByUser(UserPrincipal user) {
        return imageRepository.findAllByCreatedBy(user.getId()).stream()
                .map(x -> {
                    try {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        BufferedImage img = Thumbnails.of(x.getPath()).size(Thumbnail.WIDTH,Thumbnail.HEIGHT).asBufferedImage();
                        ImageIO.write(img,"JPG", os);
                        return new Thumbnail("data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(os.toByteArray()),
                                "",
                                x.getId(),
                                img.getWidth(),
                                img.getHeight()
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> deleteImage(Long id) {
        try {
            Image image = imageRepository.getOne(id);
            File file = new File(image.getPath());
            file.delete();
            imageRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "No such content!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Image deleted!"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> deleteMod(Long id) {
        try {
            modRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "No such content!!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Modification deleted!!"),
                HttpStatus.OK);
    }

    public ResponseEntity<?> addMod(Long imageId, Mod mod) {
        try {
            mod.setImage(imageRepository.getOne(imageId));
            modRepository.save(mod);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false, "Error! Modification failed!!"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Modification added!"),
                HttpStatus.CREATED);
    }
}
