package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.converter.FollowConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.BlockedFollower;
import com.example.instaclone_9room.domain.follow.CloseFollower;
import com.example.instaclone_9room.domain.follow.Follow;
import com.example.instaclone_9room.domain.follow.Follower;
import com.example.instaclone_9room.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final BlockedFollowerRepository blockedFollowerRepository;


    @Override
    public void followUser(String username, Long targetId) {

        UserEntity follower = findUser(username);
        UserEntity target = userRepository.findById(targetId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        // 팔로우 추가
        Follow follow = Follow.builder()
                .userEntity(follower)
                .followUser(target)
                .build();
        followRepository.save(follow);

        // 팔로워 추가
        Follower followerEntity = Follower.builder()
                .userEntity(target)
                .followerUser(follower)
                .build();
        followerRepository.save(followerEntity);
    }


    @Override
    public void unfollowUser(String username, Long targetId) {
        UserEntity follower = findUser(username);
        UserEntity target = userRepository.findById(targetId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        // 팔로우 제거
        followRepository.deleteByUserEntityAndFollowUser(follower, target);

        // 팔로워 제거
        followerRepository.deleteByUserEntityAndFollowerUser(target, follower);
    }


    @Override
    public List<FollowDTO.FollowResponseDTO> getFollowedUsers(String username) {
        UserEntity user = findUser(username);

        // 해당 유저가 팔로우하는 유저 목록 조회
        List<Follow> follows = followRepository.findByUserEntity(user);

        // Follow 객체에서 UserEntity를 추출하고, 이를 FollowResponseDTO로 변환
        return follows.stream()
                .map(follow -> FollowConverter.toFollowResponseDTO(follow.getFollowUser()))
                .collect(Collectors.toList());
    }


    @Override
    public List<FollowDTO.FollowerResponseDTO> getFollowers(String username) {

        UserEntity user = findUser(username);
        List<Follower> followers = followerRepository.findByUserEntity(user);

        return followers.stream()
                .map(follower -> FollowConverter.toFollowerResponseDTO(follower.getFollowerUser()))
                .collect(Collectors.toList());
    }



    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}



