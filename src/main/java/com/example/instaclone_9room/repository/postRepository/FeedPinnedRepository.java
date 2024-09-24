package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedPinned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedPinnedRepository extends JpaRepository<FeedPinned, Long> {
    
    Optional<FeedPinned> findByUserEntityAndFeed(UserEntity userEntity, Feed feed);
    
    List<FeedPinned> findByUserEntity(UserEntity userEntity);
}
