package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReelsCommentService {
    void save(ReelsCommentDTO.CommentPostRequestDTO request, String username);


    void update(String content, Long id, String username);

    void delete(String username, Long commentId);
}
