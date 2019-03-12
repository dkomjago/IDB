package idb.repository;

import idb.entity.image.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
        Image findByImageId(Long id);
        long countByCreatedBy(Long userId);
}