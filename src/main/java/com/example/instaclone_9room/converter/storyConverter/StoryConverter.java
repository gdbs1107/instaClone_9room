package com.example.instaclone_9room.converter.storyConverter;

import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.storyEntitiy.Story;

import java.util.List;
import java.util.stream.Collectors;

public class StoryConverter {
    public static Story toStory(Story story, UserEntity user) {
        
        return Story.builder()
                .userEntity(user)
                .imagePath(story.getImagePath())
                .fileName(story.getFileName())
                .likesCount(0)
                .viewable(true)
                .build();
    }
    
    public static StoryDTO.StoryResponseDTO toStoryResponseDTO(Story story) {
        return StoryDTO.StoryResponseDTO.builder()
                .id(story.getId())
                .fileName(story.getFileName())
                .likesCount(story.getLikesCount())
                .imagePath(story.getImagePath())
                .build();
    }
    
    public static List<StoryDTO.StoryResponseDTO> toStoryResponseDTOList(List<Story> storyList) {
        return storyList.stream()
                .map(StoryConverter::toStoryResponseDTO)
                .collect(Collectors.toList());
    }
    
    public static List<StoryDTO.StoryResponseDTO> toViewableStoryResponseDTOList(List<Story> storyList) {
        
        return storyList.stream()
                .peek(Story::editViewable)
                .filter(Story::getViewable)
                .map(StoryConverter::toStoryResponseDTO)
                .collect(Collectors.toList());
    }
    
}
