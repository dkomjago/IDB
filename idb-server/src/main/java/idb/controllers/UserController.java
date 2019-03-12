package idb.controllers;

import idb.entity.user.payload.ActiveUsers;
import idb.entity.user.payload.UserIdentityAvailability;
import idb.entity.user.payload.UserProfile;
import idb.entity.user.payload.UserSummary;
import idb.security.CurrentUser;
import idb.security.UserPrincipal;
import idb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return userService.checkUsernameAvailability(username);
    }

    @GetMapping("/users/active")
    public ActiveUsers getActiveUsers(){
        return userService.getActiveUsers();
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        return userService.getUserProfile(username);
    }
}