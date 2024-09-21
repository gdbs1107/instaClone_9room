package com.example.instaclone_9room.controller.dto.storyDTO;

import com.example.instaclone_9room.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoryDTO {
    
    //--------------responseDTO---------------//
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoryResponseDTO {
        //스토리 정보
        private Long id;
        private String imagePath;
        private String fileName;
        private Integer likesCount;
    }
}
