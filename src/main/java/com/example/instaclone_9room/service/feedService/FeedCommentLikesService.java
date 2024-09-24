package com.example.instaclone_9room.service.feedService;

import org.springframework.stereotype.Service;

@Service
public interface FeedCommentLikesService {
    void toggleFeedCommentLike(Long commentId, String username);
}
