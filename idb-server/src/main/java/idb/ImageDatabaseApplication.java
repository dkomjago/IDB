package idb;

import idb.entity.user.Role;
import idb.entity.user.RoleName;
import idb.entity.user.User;
import idb.repository.RoleRepository;
import idb.repository.UserRepository;
import idb.security.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;

@SpringBootApplication
public class ImageDatabaseApplication {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ImageDatabaseApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImageDatabaseApplication.class, args);
    }

    @PostConstruct
    public void initData() {
        // Adding USER role if nonexistent
        if (roleRepository.count()==0) {
            Role role = new Role();
            role.setName(RoleName.ROLE_USER);
            roleRepository.save(role);
        }

        // Creating test account
        if(!userRepository.existsByUsername("tester")) {
            User user = new User();
            user.setUsername("tester");
            user.setPassword(passwordEncoder.encode("tester"));

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));
            userRepository.save(user);
        }
    }

    /**
     * Clean up saved files on exit (for inmemory database)
     */
    @Profile("h2")
    @PreDestroy
    public void clearStorage() {
        try {
            Path path = Paths.get("storage");
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
