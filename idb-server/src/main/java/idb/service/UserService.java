package idb.service;

import idb.entity.user.User;
import idb.entity.user.payload.ActiveUsers;
import idb.entity.user.payload.UserIdentityAvailability;
import idb.entity.user.payload.UserProfile;
import idb.entity.user.payload.UserSummary;
import idb.repository.ImageRepository;
import idb.repository.UserRepository;
import idb.security.UserPrincipal;
import idb.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername());
    }

    public UserIdentityAvailability checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    public ActiveUsers getActiveUsers(){
        List<User> activeUserList = userRepository.findAllByActive(true);
        HashMap<Long,String> activeUserData = new HashMap<>();
        activeUserList.forEach(x -> activeUserData.put(x.getUserId(),x.getUsername()));
        return new ActiveUsers(activeUserData);
    }

    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long imageCount = imageRepository.countByCreatedBy(user.getUserId());

        return new UserProfile(user.getUserId(), user.getUsername(), imageCount);
    }
}