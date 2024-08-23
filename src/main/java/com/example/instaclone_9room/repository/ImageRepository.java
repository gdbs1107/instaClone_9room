package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
