package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsComment;

import java.util.List;

public class ReelsCommentConverter {
    public static ReelsComment toReelsComment(ReelsCommentDTO.CommentPostRequestDTO request,
                                              UserEntity userEntity,
                                              Reels reels) {
        return ReelsComment.builder()
                .content(request.getContent())
                .userEntity(userEntity)
                .reels(reels)
                .build();
    }
}
