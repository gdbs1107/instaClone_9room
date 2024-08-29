package com.example.instaclone_9room.service.followService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.FollowCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.converter.FollowConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.CloseFollower;
import com.example.instaclone_9room.domain.follow.Follower;
import com.example.instaclone_9room.repository.followRepository.CloseFollowerRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClosedFollowServiceImpl implements ClosedFollowService {

    private final UserRepository userRepository;
    private final CloseFollowerRepository closeFollowerRepository;

    @Override
    public void toggleCloseFollower(String username, Long followerId) {
        // 사용자 찾기
        UserEntity user = findUser(username);

        // 팔로워 찾기
        Follower follower = user.getFollowers().stream()
                .filter(f -> f.getId().equals(followerId))
                .findFirst()
                .orElseThrow(()->new FollowCategoryHandler(ErrorStatus.NOT_YOUR_FOLLOWER));

        // CloseFollower 찾기
        CloseFollower closeFollower = closeFollowerRepository.findByUserEntityAndFollower(user, follower)
                .orElse(null);

        if (closeFollower != null) {
            // 이미 친한 팔로워인 경우, 삭제
            closeFollowerRepository.delete(closeFollower);
        } else {
            // 친한 팔로워가 아닌 경우, 추가
            CloseFollower newCloseFollower = CloseFollower.builder()
                    .userEntity(user)
                    .follower(follower)
                    .build();

            closeFollowerRepository.save(newCloseFollower);
        }
    }

    @Override
    public List<FollowDTO.FollowerResponseDTO> getCloseFollowers(String username){
        UserEntity user = findUser(username);

        List<CloseFollower> finds = closeFollowerRepository.findByUserEntity(user);

        return finds.stream()
                .map(find -> FollowConverter.toFollowerResponseDTO(find.getUserEntity()))
                .collect(Collectors.toList());
    }




    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }
}
