package com.example.instaclone_9room.repository.storyRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {
    
    List<Story> findAllById(Long id);
    
    List<Story> findByUserEntity(UserEntity userEntity);
    
    Optional<Story> findByFileName(String fileName);
}
