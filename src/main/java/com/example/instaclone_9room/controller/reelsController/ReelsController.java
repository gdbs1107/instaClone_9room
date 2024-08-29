package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{reelsId}")
    public ApiResponse<String> delete(@PathVariable Long reelsId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.deleteReels(userDetails.getUsername(),reelsId);
        return ApiResponse.onSuccess("deleted reels");
    }

    @GetMapping("/{reelsId}")
    public ApiResponse<ReelsDTO.ReelsResponseDTO> getReels(@PathVariable Long reelsId) {
        ReelsDTO.ReelsResponseDTO reels = reelsService.getReels(reelsId);
        return ApiResponse.onSuccess(reels);
    }

    @PutMapping("/{reelsId}")
    public ApiResponse<String> updateReels(@PathVariable Long reelsId,
                                           @RequestBody ReelsDTO.ReelsUpdateRequestDTO request,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.updateReels(userDetails.getUsername(),reelsId,request);
        return ApiResponse.onSuccess("updated reels");
    }


    //릴스 전체조회 API 만들어야함
}
