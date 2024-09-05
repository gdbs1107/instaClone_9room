package com.example.instaclone_9room.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class JoinDto {


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinRequestDTO{

        @NotNull(message = "null값입니다")
        private String username;

        @NotNull(message = "null값입니다")
        private String password;


        private String name;
        private Integer genderType;
        private LocalDate birthday;
        private String link;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDto{

        private String username;
        private String password;

    }


}
