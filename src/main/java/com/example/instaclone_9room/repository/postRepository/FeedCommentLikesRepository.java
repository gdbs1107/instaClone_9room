package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.feedEntity.FeedCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentLikesRepository extends JpaRepository<FeedCommentLikes, Long> {
}
