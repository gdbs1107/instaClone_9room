package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.controller.dto.FollowDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlockedFollowerService {
    void toggleBlockedFollower(String username, Long followerId);

    List<FollowDTO.FollowerResponseDTO> getBlockedFollowers(String username);
}
