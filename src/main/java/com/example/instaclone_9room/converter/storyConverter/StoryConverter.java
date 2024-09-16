package com.example.instaclone_9room.converter.storyConverter;

import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import com.example.instaclone_9room.domain.storyEntitiy.Story;

import java.util.List;

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
                .fileName(story.getFileName())
                .likesCount(story.getLikesCount())
                .imagePath(story.getImagePath())
                .build();
    }
    
}
