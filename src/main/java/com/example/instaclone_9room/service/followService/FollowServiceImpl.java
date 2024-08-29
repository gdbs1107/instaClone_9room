package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.converter.FollowConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.Follow;
import com.example.instaclone_9room.domain.follow.Follower;
import com.example.instaclone_9room.repository.*;
import com.example.instaclone_9room.repository.followRepository.BlockedFollowerRepository;
import com.example.instaclone_9room.repository.followRepository.FollowRepository;
import com.example.instaclone_9room.repository.followRepository.FollowerRepository;
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
    public void toggleFollowUser(String username, Long targetId) {

        // 팔로우 신청하는 사람 -> follower
        UserEntity follower = findUser(username);

        // 팔로우 받는 사람 -> target
        UserEntity target = userRepository.findById(targetId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        // 이미 팔로우하고 있는지 확인
        boolean isFollowing = followRepository.existsByUserEntityAndFollowUser(follower, target);

        if (isFollowing) {
            // 팔로우 중이라면 언팔로우
            followRepository.deleteByUserEntityAndFollowUser(follower, target);
            follower.minusFollowCount();

            followerRepository.deleteByUserEntityAndFollowerUser(target, follower);
            target.minusFollowerCount();
        } else {
            // 팔로우 중이 아니라면 팔로우
            Follow follow = Follow.builder()
                    .userEntity(follower)
                    .followUser(target)
                    .build();
            follower.addFollowCount();
            followRepository.save(follow);

            Follower followerEntity = Follower.builder()
                    .userEntity(target)
                    .followerUser(follower)
                    .build();
            target.addFollowerCount();
            followerRepository.save(followerEntity);
        }
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
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }


}



