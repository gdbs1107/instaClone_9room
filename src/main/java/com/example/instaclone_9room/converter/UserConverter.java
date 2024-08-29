package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;

public class UserConverter {


    public static UserDTO.UserGetHomeResponseDTO toUserGetHomeResponseDTO(UserEntity userEntity) {

        return UserDTO.UserGetHomeResponseDTO.builder()
                .name(userEntity.getName())
                .link(userEntity.getLink())
                .followCount(userEntity.getFollowCount())
                .followersCount(userEntity.getFollowerCount())
                .imagePath(userEntity.getImagePath())
                .build();
    }



    public static Gender toGender(Integer genderType){
        if(genderType == null){
            return null;
        }

        switch (genderType){
            case 1:
                return Gender.male;
                case 2:
                    return Gender.female;
            default:
                throw new IllegalArgumentException("Invalid genderType: " + genderType);
        }
    }
}
