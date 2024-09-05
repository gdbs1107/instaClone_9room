package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;

import java.util.List;
import java.util.stream.Collectors;

public class ReelsConverter {


    public static Reels toReels(ReelsDTO.ReelsRequestDTO reelsDTO, UserEntity user) {
        return Reels.builder()
                .userEntity(user)
                .audioName(reelsDTO.getAudioName())
                .audioPath(reelsDTO.getAudioPath())
                .videoPath(reelsDTO.getVideoPath())
                .videoName(reelsDTO.getVideoName())
                .content(reelsDTO.getContent())
                .commentCount(0)
                .likesCount(0)
                .pinnedCount(0)
                .build();
    }

    public static ReelsDTO.ReelsResponseDTO toReelsResponseDTO(Reels reels, UserEntity user) {
        List<ReelsCommentDTO.CommentPostResponseDTO> reelsCommentDTOs = reels.getReelsComments().stream()
                .map(comment -> ReelsCommentDTO.CommentPostResponseDTO.builder()
                        .content(comment.getContent())
                        .name(comment.getUserEntity().getName())
                        .build())
                .collect(Collectors.toList());

        return ReelsDTO.ReelsResponseDTO.builder()
                .videoPath(reels.getVideoPath())
                .audioPath(reels.getAudioPath())
                .reelsComments(reelsCommentDTOs)
                .commentCount(reels.getCommentCount())
                .likeCount(reels.getLikesCount())
                .content(reels.getContent())
                .imagePath(user.getImagePath())
                .name(user.getName())
                .build();
    }

}
