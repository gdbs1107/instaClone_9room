package com.example.instaclone_9room.controller.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedCommentDTO {
    
    @Getter
    public static class CommentPostRequestDTO {
        private String content;
        private Long feedId;
    }
    
    @Getter
    public static class CommentUpdateRequestDTO {
        private String content;
        private Long commentId;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentResponseDTO {
        private String name;
        private String content;
    
    }
}
