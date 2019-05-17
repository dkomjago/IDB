package idb.service;

import idb.security.payload.LoginRequest;
import idb.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(RegisterRequest registerRequestRequest);
}
