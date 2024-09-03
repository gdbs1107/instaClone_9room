package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.service.reelsService.ReelsLikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "릴스 좋아요 API", description = "릴스 좋아요 관련 API입니다.")
public class ReelsLikesController {

    private final ReelsLikesService reelsLikesService;



    @Operation(
            summary = "릴스 좋아요 토글 API",
            description = "릴스 좋아요를 저장/삭제하는 토글 API입니다.<br>" +
                    " 헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "한 번 클릭하면 좋아요, 두번 클릭하면 좋아요가 취소됩니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REELS4001", description = "릴스를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{reelsId}")
    public ApiResponse<String> toogleLike(@PathVariable Long reelsId,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        reelsLikesService.toggleReelsLike(userDetails.getUsername(), reelsId);
        return ApiResponse.onSuccess("success");
    }
}
