package idb.entity.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for sending information about currently logged in user
 */
@Getter
@Setter
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
}
