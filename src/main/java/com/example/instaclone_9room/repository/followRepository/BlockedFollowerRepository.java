package com.example.instaclone_9room.repository.followRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.follow.BlockedFollower;
import com.example.instaclone_9room.domain.follow.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockedFollowerRepository extends JpaRepository<BlockedFollower, Long> {
    Optional<BlockedFollower> findByUserEntityAndFollower(UserEntity userEntity, Follower follower);
    List<BlockedFollower> findByUserEntity(UserEntity userEntity);
}
