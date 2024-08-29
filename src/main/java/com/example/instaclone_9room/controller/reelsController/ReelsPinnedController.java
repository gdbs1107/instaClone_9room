package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsPinnedService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reelsPinned")
public class ReelsPinnedController {

    private final ReelsPinnedService reelsPinnedService;

    @PostMapping("/{reelsId}")
    public ApiResponse<String> reelsPinned(@PathVariable("reelsId") Long reelsId,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        reelsPinnedService.savePinnedReels(userDetails.getUsername(), reelsId);
        return ApiResponse.onSuccess("save Successfully");
    }


    @GetMapping("/")
    private ApiResponse<List<ReelsDTO.ReelsResponseDTO>> getAllReelsPinned(@AuthenticationPrincipal UserDetails userDetails) {

        List<ReelsDTO.ReelsResponseDTO> reelsPinned = reelsPinnedService.getReelsPinned(userDetails.getUsername());
        return ApiResponse.onSuccess(reelsPinned);
    }
}
