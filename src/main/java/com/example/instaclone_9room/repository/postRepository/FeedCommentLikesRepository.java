package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.FeedComment;
import com.example.instaclone_9room.domain.feedEntity.FeedCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedCommentLikesRepository extends JpaRepository<FeedCommentLikes, Long> {
    Optional<FeedCommentLikes> findByUserEntityAndFeedComment(UserEntity userEntity, FeedComment feedComment);
}
