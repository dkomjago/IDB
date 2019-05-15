package idb.entity.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * DTO for sending online user names and id's
 */
@Getter
@Setter
@AllArgsConstructor
public class ActiveUsers {
    HashMap<Long,String> activeUsers;
}
