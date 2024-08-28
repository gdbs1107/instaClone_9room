package com.example.instaclone_9room.controller.dto;

import lombok.Getter;

public class ReelsDTO {

    @Getter
    public static class ReelsRequestDTO{

        private String videoName;
        private String videoPath;
        private String audioName;
        private String audioPath;


    }
}
