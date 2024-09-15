package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedCommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface FeedCommentService {
    void save(FeedCommentDTO.CommentPostRequestDTO request, String username);
    
    void update(String content, Long id, String username);
    
    void delete(String username, Long commentId);
}
