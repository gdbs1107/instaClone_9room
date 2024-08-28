package com.example.instaclone_9room.controller.dto;

import com.example.instaclone_9room.domain.reels.ReelsComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReelsDTO {

    @Getter
    public static class ReelsRequestDTO{

        private String videoName;
        private String videoPath;
        private String audioName;
        private String audioPath;
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReelsResponseDTO{

        private String videoPath;
        private String audioPath;

        private List<ReelsCommentDTO.CommentPostResponseDTO> reelsComments;
    }

    @Getter
    public static class ReelsUpdateRequestDTO{

        private String audioName;
        private String audioPath;
        private String content;

    }
}
