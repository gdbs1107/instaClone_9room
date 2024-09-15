package com.example.instaclone_9room.controller.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ImageDTO {
    
    @Getter
    public static class ImageRequestDTO {
        
        private String imagePath;
        private String fileName;
    }
    
    @Getter
    public static class ImagesSearchDTO {
        private List<String> fileName;
    }
    
    @Getter
    public static class ImageSearchDTO {
        private String fileName;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageResponseDTO {
        
        private String imagePath;
        private String fileName;
    }
    
}
