package idb.repository;


import idb.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAllByActive(boolean active);
    boolean existsByUsername(String username);
}
