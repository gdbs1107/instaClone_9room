package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedService {
    
    void postFeed(FeedDTO.FeedPostRequestDTO feedPostRequestDTO, String username);
    
    void updateFeed(Long feedId, FeedDTO.FeedUpdateRequestDTO feedUpdateRequestDTO, String username);
    
    void deleteFeed(Long feedId, String username);
    
    FeedDTO.FeedResponseDTO searchFeed(Long feedId);
    
    List<FeedDTO.FeedSmallResponseDTO> searchFeedByUsername(String username);
}
