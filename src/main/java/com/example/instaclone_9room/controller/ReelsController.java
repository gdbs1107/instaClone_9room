package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reels")
public class ReelsController {

    private final ReelsService reelsService;

    @PostMapping("/")
    public ApiResponse<String> save(@RequestBody ReelsDTO.ReelsRequestDTO requestDTO,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        reelsService.save(requestDTO,userDetails.getUsername());

        return ApiResponse.onSuccess("saved reels");
    }
}
