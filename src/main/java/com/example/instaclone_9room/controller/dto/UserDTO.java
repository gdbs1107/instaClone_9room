package com.example.instaclone_9room.controller.dto;

import com.example.instaclone_9room.domain.enumPackage.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String name;
    private String role;




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


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetHomeResponseDTO{

        Integer followCount;
        Integer followersCount;
        String name;
        String imagePath;
        String link;

        //게시물 수, 전체 게시물 조회 API가 여기 들어가야함 나중에 머지할때 보자

    }


}
