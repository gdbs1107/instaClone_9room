package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;

public interface UserCommandService {
    void updateUser(UserDTO.UserUpdateRequestDTO request, String username);

    void deleteUser(String username);

    void logout(String refreshToken);

    void joinProcess(JoinDto.JoinRequestDTO joinDto);

    UserDTO.UserGetResponseDTO getUserProfile(String username);

    UserDTO.UserGetHomeResponseDTO userGetHomeProfile(String username);

    UserDTO.UserGetResponseDTO getUserProfileByUsername(String targetUsername, String requestingUsername);
}
