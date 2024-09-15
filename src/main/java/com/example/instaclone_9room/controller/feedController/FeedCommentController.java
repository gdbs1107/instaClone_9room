package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.postDTO.FeedCommentDTO;
import com.example.instaclone_9room.service.feedService.FeedCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedComment")
public class FeedCommentController {
    
    private final FeedCommentService feedCommentService;
    
    @PostMapping("/")
    public ApiResponse<String> saveComment(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody @Valid FeedCommentDTO.CommentPostRequestDTO requestDTO) {
        feedCommentService.save(requestDTO, userDetails.getUsername());
        return ApiResponse.onSuccess("saved comment successfully");
    }
    
    @PutMapping("/{id}")
    public ApiResponse<String> update(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id,
                                      @RequestBody @Valid String content){
        
        feedCommentService.update(content, id, userDetails.getUsername());
        return ApiResponse.onSuccess("updated comment successfully");
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id){
        feedCommentService.delete(userDetails.getUsername(), id);
        return ApiResponse.onSuccess("deleted comment successfully");
    }
}
