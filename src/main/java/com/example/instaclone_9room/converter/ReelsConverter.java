package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;

public class ReelsConverter {


    public static Reels toReels(ReelsDTO.ReelsRequestDTO reelsDTO, UserEntity user) {
        return Reels.builder()
                .userEntity(user)
                .audioName(reelsDTO.getAudioName())
                .audioPath(reelsDTO.getAudioPath())
                .videoPath(reelsDTO.getVideoPath())
                .videoName(reelsDTO.getVideoName())
                .build();
    }
}
