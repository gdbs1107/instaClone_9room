package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.service.chatService.ChatService;
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
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "채팅방 API", description = "소켓 통신을 제외한 비즈니스 로직들이 해당됩니다. (채팅방 생성, 삭제, 목록 조회 등)- 함윤")
public class ChatController {

    private final ChatService chatService;

    @Operation(
            summary = "채팅방 생성 API",
            description = "채팅방을 생성하는 API입니다. ws api 사용 전 필수적으로 이루어져야합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PostMapping("/chatroom/create")
    public ApiResponse<ChatDTO.ChatRoomCreateResp> chatRoomCreate(@RequestBody @Valid ChatDTO.ChatRoomCreateDTO reqeust,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        ChatDTO.ChatRoomCreateResp response = chatService.chatRoomCreate(reqeust, userDetails.getUsername());

        return ApiResponse.onSuccess(response);

    }

}