package com.example.instaclone_9room.service.storyService;

import org.springframework.stereotype.Service;

@Service
public interface StoryLikesService {
    
    void toggleStoryLike(Long storyId, String username);
}
