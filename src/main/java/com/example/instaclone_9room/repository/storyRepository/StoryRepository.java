package com.example.instaclone_9room.repository.storyRepository;

import com.example.instaclone_9room.domain.storyEntitiy.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
