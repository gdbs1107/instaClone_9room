package com.example.instaclone_9room.repository.storyRepository;

import com.example.instaclone_9room.domain.storyEntitiy.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    
}
