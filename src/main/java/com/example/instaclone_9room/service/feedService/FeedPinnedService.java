package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedPinnedService {
    void savePinnedFeed(String username, Long feedId);
    
    List<FeedDTO.FeedResponseDTO> getPinnedFeed(String username);
}
