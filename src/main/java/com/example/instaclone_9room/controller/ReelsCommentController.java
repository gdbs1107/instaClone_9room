package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import com.example.instaclone_9room.domain.reels.ReelsComment;
import com.example.instaclone_9room.service.reelsService.ReelsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reelsComment")
public class ReelsCommentController {

    private final ReelsCommentService reelsCommentService;

    @PostMapping("/")
    public ApiResponse<String> save(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody ReelsCommentDTO.CommentPostRequestDTO requestDTO) {

        reelsCommentService.save(requestDTO,userDetails.getUsername());
        return ApiResponse.onSuccess("saved comment successfully");
    }



    @PutMapping("/{id}")
    public ApiResponse<String> update(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id,
                                      @RequestBody String content){

        reelsCommentService.update(content,id,userDetails.getUsername());
        return ApiResponse.onSuccess("updated comment successfully");
    }




    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id){
        reelsCommentService.delete(userDetails.getUsername(),id);
        return ApiResponse.onSuccess("deleted comment successfully");
    }
}
