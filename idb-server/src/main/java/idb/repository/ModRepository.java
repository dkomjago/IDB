package idb.repository;

import idb.entity.image.Mod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ModRepository extends JpaRepository<Mod, Long> {
    Set<Mod> findAllByImageId(Long imageId);
}
