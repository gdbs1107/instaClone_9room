package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.converter.FollowConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.BlockedFollower;
import com.example.instaclone_9room.domain.follow.Follower;
import com.example.instaclone_9room.repository.followRepository.BlockedFollowerRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BlockedFollowerServiceImpl implements BlockedFollowerService {

    private final BlockedFollowerRepository blockedFollowerRepository;
    private final UserRepository userRepository;

    @Override
    public void toggleBlockedFollower(String username, Long followerId) {
        // 사용자 찾기
        UserEntity user = findUser(username);

        // 팔로워 찾기
        Follower follower = user.getFollowers().stream()
                .filter(f -> f.getId().equals(followerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        // CloseFollower 찾기
        BlockedFollower blockedFollower = blockedFollowerRepository.findByUserEntityAndFollower(user, follower)
                .orElse(null);

        if (blockedFollower != null) {
            // 이미 친한 팔로워인 경우, 삭제
            blockedFollowerRepository.delete(blockedFollower);
        } else {
            // 친한 팔로워가 아닌 경우, 추가
            BlockedFollower newBlockedFollower = BlockedFollower.builder()
                    .userEntity(user)
                    .follower(follower)
                    .build();

            blockedFollowerRepository.save(newBlockedFollower);
        }
    }


    @Override
    public List<FollowDTO.FollowerResponseDTO> getBlockedFollowers(String username){
        UserEntity user = findUser(username);

        List<BlockedFollower> finds = blockedFollowerRepository.findByUserEntity(user);

        return finds.stream()
                .map(find -> FollowConverter.toFollowerResponseDTO(find.getUserEntity()))
                .collect(Collectors.toList());
    }


    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
