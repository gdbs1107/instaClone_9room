package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedLikesRepository extends JpaRepository<FeedLikes, Long> {

    Optional<FeedLikes> findByUserEntityAndFeed(UserEntity userEntity, Feed feed);

}
