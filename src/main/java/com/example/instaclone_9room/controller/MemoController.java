package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.MemoDTO;
import com.example.instaclone_9room.service.memoService.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
@Tag(name = "메모 API", description = "인스타 DM 화면에 나오는 메모 기능 API입니다 - 함윤")
public class MemoController {

    private final MemoService memoService;

    @Operation(
            summary = "메모 작성 API",
            description = "메모 작성 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PostMapping("/")
    public ApiResponse<String> save(@RequestBody @Valid MemoDTO.MemoCreateDTO requestDTO,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        memoService.create(requestDTO, userDetails.getUsername());

        return ApiResponse.onSuccess("create memo");
    }

    @Operation(
            summary = "메모 삭제 API",
            description = "메모 삭제 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @DeleteMapping("/{memoId}")
    public ApiResponse<String> delete(@PathVariable Long memoId, @AuthenticationPrincipal UserDetails userDetails) {
        memoService.delete(userDetails.getUsername(), memoId);

        return ApiResponse.onSuccess("delete memo");
    }

    @Operation(
            summary = "메모 수정 API",
            description = "메모 수정 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PatchMapping("/{memoId}")
    public ApiResponse<String> update(@PathVariable Long memoId,
                                      @RequestBody MemoDTO.MemoUpdateDTO requestDTO,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        memoService.update(userDetails.getUsername(), memoId, requestDTO);

        return ApiResponse.onSuccess("update memo");
    }
}
