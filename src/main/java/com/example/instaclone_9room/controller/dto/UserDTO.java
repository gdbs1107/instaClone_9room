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
    public static class UserJoinRequestDTO{


        private String name;

        private Integer genderType;
        private LocalDate birthday;
        private String link;

    }
}
