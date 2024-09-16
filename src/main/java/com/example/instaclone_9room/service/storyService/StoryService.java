package com.example.instaclone_9room.service.storyService;

import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import org.springframework.web.multipart.MultipartFile;

public interface StoryService {
    
    void postStory(MultipartFile file, String username, String dirName);
    
    void deleteStory(Long storyId, String username);
    
    StoryDTO.StoryResponseDTO searchStory(Long storyId);
}
