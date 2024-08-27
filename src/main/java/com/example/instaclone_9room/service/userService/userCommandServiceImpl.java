package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.converter.UserConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;
import com.example.instaclone_9room.jwt.JwtUtil;
import com.example.instaclone_9room.repository.RefreshRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userCommandServiceImpl implements UserCommandService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;




    @Override
    public void logout(String refreshToken) {
        if (refreshToken == null) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("refresh expired token");
        }

        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            throw new RuntimeException("refresh Token null");
        }

        String username = jwtUtil.getUsername(refreshToken);

        refreshRepository.deleteByRefresh(refreshToken);
    }


    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new RuntimeException("cannot find users")
        );
    }
}
