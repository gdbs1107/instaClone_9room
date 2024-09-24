package com.example.instaclone_9room.repository.storyRepository;

import com.example.instaclone_9room.domain.storyEntitiy.Highlight;
import com.example.instaclone_9room.domain.storyEntitiy.StoryHighlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryHighlightRepository extends JpaRepository<StoryHighlight, Long> {
    
    List<StoryHighlight> findAllByHighlight(Highlight highlight);
}
