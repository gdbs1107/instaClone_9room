package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsPinnedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "릴스 저장 API", description = "릴스 저장 관련 API입니다.")
public class ReelsPinnedController {

    private final ReelsPinnedService reelsPinnedService;


    @Operation(
            summary = "릴스 저장 토글 API",
            description = "릴스 저장 토글 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다<br>" +
                    "한 번 누르면 저장, 두번 누르면 삭제됩니다"
    )
    @PostMapping("/{reelsId}")
    public ApiResponse<String> reelsPinned(@PathVariable("reelsId") Long reelsId,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        reelsPinnedService.savePinnedReels(userDetails.getUsername(), reelsId);
        return ApiResponse.onSuccess("save Successfully");
    }


    @Operation(
            summary = "저장된 릴스 조회 API",
            description = "저장된 릴스를 조회하는 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @GetMapping("/")
    private ApiResponse<List<ReelsDTO.ReelsResponseDTO>> getAllReelsPinned(@AuthenticationPrincipal UserDetails userDetails) {

        List<ReelsDTO.ReelsResponseDTO> reelsPinned = reelsPinnedService.getReelsPinned(userDetails.getUsername());
        return ApiResponse.onSuccess(reelsPinned);
    }
}
