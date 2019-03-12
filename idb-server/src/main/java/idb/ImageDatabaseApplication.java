package idb;

import idb.repository.UserRepository;
import idb.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageDatabaseApplication {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;

    public static void main(String[] args) {
        SpringApplication.run(ImageDatabaseApplication.class, args);
    }

   /* @PostConstruct
    public void setupDbWithData(){
        User user= new User("kek","kekson");
        userRepository.save(user);
    }
    */

}
