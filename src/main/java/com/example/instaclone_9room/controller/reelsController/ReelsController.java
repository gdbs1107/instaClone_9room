package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.service.reelsService.ReelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
                    "웹에선 지원하지 않는 기능입니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/")
    public ApiResponse<String> save(@RequestBody @Valid ReelsDTO.ReelsRequestDTO requestDTO,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        reelsService.save(requestDTO,userDetails.getUsername());

        return ApiResponse.onSuccess("saved reels");
    }



    @Operation(
            summary = "릴스 삭제 API",
            description = "릴스 삭제 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
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
    @DeleteMapping("/{reelsId}")
    public ApiResponse<String> delete(@PathVariable Long reelsId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.deleteReels(userDetails.getUsername(),reelsId);
        return ApiResponse.onSuccess("deleted reels");
    }



    @Operation(
            summary = "단일 릴스 조회 API",
            description = "릴스 하나를 조회하는 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
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
    @GetMapping("/{reelsId}")
    public ApiResponse<ReelsDTO.ReelsResponseDTO> getReels(@PathVariable Long reelsId) {
        ReelsDTO.ReelsResponseDTO reels = reelsService.getReels(reelsId);
        return ApiResponse.onSuccess(reels);
    }





    @Operation(
            summary = "릴스 수정 API",
            description = "릴스 수정 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다<br>" +
                    "웹에서는 지원하지 않는 기능입니다"
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
    @PutMapping("/{reelsId}")
    public ApiResponse<String> updateReels(@PathVariable Long reelsId,
                                           @RequestBody @Valid ReelsDTO.ReelsUpdateRequestDTO request,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        reelsService.updateReels(userDetails.getUsername(),reelsId,request);
        return ApiResponse.onSuccess("updated reels");
    }


    @Operation(
            summary = "릴스 페이지네이션 조회 API",
            description = "릴스를 한 번에 하나씩 조회하는 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다. \n" +
                    "페이지 번호를 QueryParam으로 전달하여 원하는 페이지의 릴스를 조회할 수 있습니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REELS4001", description = "릴스를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REELS4003", description = "릴스를 전부 조회하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/paged")
    public ApiResponse<ReelsDTO.ReelsResponseDTO> getReelsByPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page) {

        ReelsDTO.ReelsResponseDTO reels = reelsService.getReelsByPage(userDetails.getUsername(), page);
        return ApiResponse.onSuccess(reels);
    }
}
