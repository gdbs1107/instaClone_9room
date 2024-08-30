package com.example.instaclone_9room.repository.followRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.follow.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    void deleteByUserEntityAndFollowerUser(UserEntity userEntity, UserEntity followUser);
    List<Follower> findByUserEntity(UserEntity userEntity);

    boolean existsByUserEntityAndFollowerUser(UserEntity userEntity, UserEntity follower);

}
