package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.feedEntity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
}
