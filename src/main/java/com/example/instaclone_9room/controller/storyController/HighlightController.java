package com.example.instaclone_9room.controller.storyController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.storyDTO.HighlightDTO;
import com.example.instaclone_9room.service.storyService.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/highlight")
public class HighlightController {
    
    private final HighlightService highlightService;
    
    @PostMapping("/")
    public ApiResponse<String> createHighlight(@RequestBody HighlightDTO.HighlightPostRequestDTO requestDTO,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        highlightService.createHighlight(requestDTO, userDetails.getUsername());
        
        return ApiResponse.onSuccess("create highlight success");
    }
    
    @DeleteMapping("/{highlightId}")
    public ApiResponse<String> deleteHighlight(@PathVariable Long highlightId) {
        
        highlightService.deleteHighlight(highlightId);
        
        return ApiResponse.onSuccess("delete highlight success");
    }
    
    @GetMapping("/{highlightId}")
    public ApiResponse<HighlightDTO.HighlightResponseDTO> getHighlight(@PathVariable Long highlightId) {
        
        return ApiResponse.onSuccess(highlightService.getHighlight(highlightId));
    }
}
