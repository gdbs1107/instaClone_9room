package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.ReelsPinnedDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsPinned;

public class ReelsPinnedConverter {

    public static ReelsPinned toReelsPinned(UserEntity user, Reels reels) {

        return ReelsPinned.builder()
                .userEntity(user)
                .reels(reels)
                .build();

    }
}