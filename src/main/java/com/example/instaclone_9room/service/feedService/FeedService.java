package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import org.springframework.stereotype.Service;

@Service
public interface FeedService {
    
    void postFeed(FeedDTO.FeedPostRequestDTO feedPostRequestDTO, String username);
}
