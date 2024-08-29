package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.service.reelsService.ReelsLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reelsLikes")
public class ReelsLikesController {

    private final ReelsLikesService reelsLikesService;


    @PostMapping("/{reelsId}")
    public ApiResponse<String> toogleLike(@PathVariable Long reelsId,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        reelsLikesService.toggleReelsLike(userDetails.getUsername(), reelsId);
        return ApiResponse.onSuccess("success");
    }
}
