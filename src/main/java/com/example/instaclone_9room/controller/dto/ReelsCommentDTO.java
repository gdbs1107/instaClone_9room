package com.example.instaclone_9room.controller.dto;

import com.example.instaclone_9room.domain.reels.Reels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReelsCommentDTO {

    @Getter
    public static class CommentPostRequestDTO{
        private String content;
        private Long reelsId;
    }

    @Getter
    public static class CommentUpdateRequestDTO{
        private String content;
        private Long commentId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentPostResponseDTO{

        private String name;
        private String content;
    }
}
