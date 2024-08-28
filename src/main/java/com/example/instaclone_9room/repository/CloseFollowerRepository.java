package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.CloseFollower;
import com.example.instaclone_9room.domain.follow.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CloseFollowerRepository extends JpaRepository<CloseFollower,Long> {
    Optional<CloseFollower> findByUserEntityAndFollower(UserEntity userEntity, Follower follower);
}
