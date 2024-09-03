package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
