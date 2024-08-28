package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.Follow;

public class FollowConverter {

    public static FollowDTO.FollowResponseDTO toFollowResponseDTO(UserEntity userEntity) {
        return FollowDTO.FollowResponseDTO.builder()
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .build();
    }

    public static FollowDTO.FollowerResponseDTO toFollowerResponseDTO(UserEntity userEntity) {
        return FollowDTO.FollowerResponseDTO.builder()
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .build();
    }
}
