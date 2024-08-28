package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.controller.dto.FollowDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    void followUser(String username, Long targetId);

    void unfollowUser(String username, Long targetId);

    List<FollowDTO.FollowResponseDTO> getFollowedUsers(String username);

    List<FollowDTO.FollowerResponseDTO> getFollowers(String username);

    void toggleCloseFollower(String username, Long followerId);

    void toggleBlockedFollower(String username, Long followerId);
}
