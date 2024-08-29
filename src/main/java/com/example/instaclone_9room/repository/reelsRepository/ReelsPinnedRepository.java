package com.example.instaclone_9room.repository.reelsRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsPinned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReelsPinnedRepository extends JpaRepository<ReelsPinned, Long> {

    Optional<ReelsPinned> findByUserEntityAndReels(UserEntity userEntity, Reels reels);
    List<ReelsPinned> findByUserEntity(UserEntity userEntity);
}
