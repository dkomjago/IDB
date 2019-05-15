package idb.entity.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for sending username availability
 */
@Getter
@Setter
@AllArgsConstructor
public class UserIdentityAvailability {
    private Boolean available;
}