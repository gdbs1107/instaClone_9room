package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.feedEntity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
