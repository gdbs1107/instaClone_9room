package com.example.instaclone_9room.controller.dto;

import com.example.instaclone_9room.domain.enumPackage.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserJoinRequestDTO{


        private String name;

        private Integer genderType;
        private LocalDate birthday;
        private String link;

    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserUpdateRequestDTO{


        private String name;

        private Integer genderType;
        private LocalDate birthday;
        private String link;
        private String introduction;
        private Boolean onPrivate;

    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetResponseDTO{

        private String name;
        private Gender genderType;
        private String link;
        private Boolean onPrivate;
        private String introduction;
    }

}
