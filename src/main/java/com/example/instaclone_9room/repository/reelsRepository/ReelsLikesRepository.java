package com.example.instaclone_9room.repository.reelsRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReelsLikesRepository extends JpaRepository<ReelsLikes,Long> {

    Optional<ReelsLikes> findByUserEntityAndReels(UserEntity userEntity, Reels reels);
}
