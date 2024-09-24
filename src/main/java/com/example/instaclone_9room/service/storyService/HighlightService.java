package com.example.instaclone_9room.service.storyService;

import com.example.instaclone_9room.controller.dto.storyDTO.HighlightDTO;
import org.springframework.stereotype.Service;

@Service
public interface HighlightService {
    
    void createHighlight(HighlightDTO.HighlightPostRequestDTO requestDTO, String username);
    
    void deleteHighlight(Long highlightId);
    
    HighlightDTO.HighlightResponseDTO getHighlight(Long highlightId);
}
