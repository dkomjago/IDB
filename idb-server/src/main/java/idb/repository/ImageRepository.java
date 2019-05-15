package idb.repository;

import idb.entity.image.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
        List<Image> findAllByCreatedBy(Long userId);
        long countByCreatedBy(Long userId);
        boolean existsByPath(String path);
        Image getByPath(String path);
}