package idb.service;

import idb.entity.user.payload.ActiveUsers;
import idb.entity.user.payload.UserIdentityAvailability;
import idb.entity.user.payload.UserProfile;
import idb.entity.user.payload.UserSummary;
import idb.security.UserPrincipal;

public interface UserService {
    UserSummary getCurrentUser(UserPrincipal currentUser);
    ActiveUsers getActiveUsers();
    UserProfile getUserProfile(String username);
    UserIdentityAvailability checkUsernameAvailability(String username);
}
