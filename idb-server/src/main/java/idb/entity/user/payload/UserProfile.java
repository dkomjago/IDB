package idb.entity.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private Long imageCount;
}
