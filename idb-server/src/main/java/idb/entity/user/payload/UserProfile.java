package idb.entity.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for sending user profile
 */
@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
}
