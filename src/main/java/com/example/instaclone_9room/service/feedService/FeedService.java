package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import org.springframework.stereotype.Service;

@Service
public interface FeedService {
    
    void postFeed(FeedDTO.FeedPostRequestDTO feedPostRequestDTO, String username);
    
    void updateFeed(Long feedId, FeedDTO.FeedUpdateRequestDTO feedUpdateRequestDTO, String username);
    
    void deleteFeed(Long feedId, String username);
    
    void searchFeed(Long feedId);
}
