package com.example.instaclone_9room.controller.dto.storyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class HighlightDTO {
    
    @Getter
    public static class HighlightPostRequestDTO {
        
        private String text;
        private List<StoryDTO.StoryRequestDTO> storyRequestDTOS;
    }
    
    @Getter
    public static class HighlightUpdateRequestDTO {
        
        private Long highlightId;
        private String text;
        private List<StoryDTO.StoryRequestDTO> storyRequestDTOS;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HighlightResponseDTO {
        
        private Long id;
        private String text;
        private List<StoryDTO.StoryResponseDTO> storyRequestDTOS;
    }
}
