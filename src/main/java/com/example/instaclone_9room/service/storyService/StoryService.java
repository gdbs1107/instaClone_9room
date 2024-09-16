package com.example.instaclone_9room.service.storyService;

import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoryService {
    
    void postStory(MultipartFile file, String username, String dirName);
    
    void deleteStory(Long storyId, String username);
    
    StoryDTO.StoryResponseDTO searchStory(Long storyId);
    
    List<StoryDTO.StoryResponseDTO> searchStoryById(Long id);
}
