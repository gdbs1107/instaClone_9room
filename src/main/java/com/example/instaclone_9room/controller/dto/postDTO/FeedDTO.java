package com.example.instaclone_9room.controller.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FeedDTO {
    
    //-------------requestDTO--------------//
    
    @Getter
    public static class FeedPostRequestDTO {
        
        private String content;
        private List<ImageDTO.ImageRequestDTO> images;
        private String location;
    }
    
    @Getter
    public static class FeedUpdateRequestDTO {
        
        private String content;
        private List<ImageDTO.ImageRequestDTO> images;
        private String location;
    }
    
    //--------------responseDTO---------------//
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeedDetailResponseDTO {
        //피드 정보
        private String content;
        private String location;
        private List<ImageDTO.ImageResponseDTO> images;
        private List<CommentDTO.CommentResponseDTO> comments;
        private Integer commentsCount;
        private Integer likesCount;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeedSmallResponseDTO {
        private String likesCount;
        private List<ImageDTO.ImageResponseDTO> images;
        private Integer commentsCount;
        
        
    }
    
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FeedSearchAllResponseDTO {
        
        private List<FeedDTO.FeedSmallResponseDTO> feeds;
    }
}
