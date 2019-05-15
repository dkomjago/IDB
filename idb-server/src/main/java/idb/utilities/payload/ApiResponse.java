package idb.utilities.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for sending request response status and string messages
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    protected Boolean success;
    protected String message;
}
