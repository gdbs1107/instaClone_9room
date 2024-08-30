package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reels")
@Tag(name = "릴스 API", description = "릴스 API입니다.")
public class ReelsController {

    private final ReelsService reelsService;


    @Operation(
            summary = "릴스 등록 API",
            description = "릴스 등록 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다<br>" +
                    "웹에선 지원하지 않는 기능입니다",
            security = @SecurityRequirement(name = "accessToken")
    )
    @PostMapping("/")
    public ApiResponse<String> save(@RequestBody @Valid ReelsDTO.ReelsRequestDTO requestDTO,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        reelsService.save(requestDTO,userDetails.getUsername());

        return ApiResponse.onSuccess("saved reels");
    }



    @Operation(
            summary = "릴스 삭제 API",
            description = "릴스 삭제 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다",
            security = @SecurityRequirement(name = "accessToken")
    )
    @DeleteMapping("/{reelsId}")
    public ApiResponse<String> delete(@PathVariable Long reelsId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.deleteReels(userDetails.getUsername(),reelsId);
        return ApiResponse.onSuccess("deleted reels");
    }



    @Operation(
            summary = "단일 릴스 조회 API",
            description = "릴스 하나를 조회하는 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다",
            security = @SecurityRequirement(name = "accessToken")
    )
    @GetMapping("/{reelsId}")
    public ApiResponse<ReelsDTO.ReelsResponseDTO> getReels(@PathVariable Long reelsId) {
        ReelsDTO.ReelsResponseDTO reels = reelsService.getReels(reelsId);
        return ApiResponse.onSuccess(reels);
    }





    @Operation(
            summary = "릴스 수정 API",
            description = "릴스 수정 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다<br>" +
                    "웹에서는 지원하지 않는 기능입니다",
            security = @SecurityRequirement(name = "accessToken")
    )
    @PutMapping("/{reelsId}")
    public ApiResponse<String> updateReels(@PathVariable Long reelsId,
                                           @RequestBody @Valid ReelsDTO.ReelsUpdateRequestDTO request,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.updateReels(userDetails.getUsername(),reelsId,request);
        return ApiResponse.onSuccess("updated reels");
    }


    //릴스 전체조회 API 만들어야함
}
