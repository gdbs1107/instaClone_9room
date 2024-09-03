package com.example.instaclone_9room.controller.dto.postDTO;

import com.example.instaclone_9room.domain.feedEntity.Comment;
import com.example.instaclone_9room.domain.feedEntity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FeedDTO {
    
    @Getter
    public static class FeedPostRequestDTO {
        
        private String content;
        private List<Image> images;
        private String location;
    }
    
    @Getter
    public static class FeedUpdateRequestDTO {
        
        private String content;
        private List<Image> images;
        private String location;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeedResponseDTO {
        //피드 정보
        private String content;
        private String location;
        private List<Image> images;
        private List<Comment> comments;
        private
        
        
    }
}
