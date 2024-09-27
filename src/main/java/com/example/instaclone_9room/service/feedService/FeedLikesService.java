package com.example.instaclone_9room.service.feedService;

import org.springframework.stereotype.Service;

@Service
public interface FeedLikesService {
    void toggleFeedLike(Long feedId, String username);
}
