package com.example.instaclone_9room.controller.dto;

import com.example.instaclone_9room.domain.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FollowDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FollowResponseDTO{

        private String username;
        private String name;

    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FollowerResponseDTO{

        private String username;
        private String name;

    }
}
