package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.feedEntity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i where i.fileName = :fileName")
    Optional<Image> findByFileName(@Param("fileName")String fileName);
}
