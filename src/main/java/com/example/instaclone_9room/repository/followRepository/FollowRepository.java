package com.example.instaclone_9room.repository.followRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByUserEntityAndFollowUser(UserEntity userEntity, UserEntity followUser);
    List<Follow> findByUserEntity(UserEntity userEntity);
}
