package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.controller.dto.UserDTO;

public interface UserCommandService {
    void logout(String refreshToken);
}
