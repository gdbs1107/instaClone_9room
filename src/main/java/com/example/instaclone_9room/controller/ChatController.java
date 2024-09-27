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
            description = "채팅방을 생성하는 API입니다. WebSocket 사용 전 필수적으로 이루어져야합니다."
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

    @Operation(
            summary = "채팅방 이름 변경 API",
            description = "채팅방 이름을 변경할 때 사용되는 API입니다. 기본적으로 여러명이 포함된 그룹 채팅에서만 사용이 가능합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PatchMapping("/update-room-name/{chatRoomId}")
    public ApiResponse<ChatDTO.ChatRoomNameUpdateResp> chatRoomNameUpdate(@RequestBody @Valid ChatDTO.ChatRoomNameUpdateDTO request,
                                                                          @PathVariable Long chatRoomId,
                                                                          @AuthenticationPrincipal UserDetails userDetails) {

        ChatDTO.ChatRoomNameUpdateResp response = chatService.setChatRoomName(request, chatRoomId, userDetails.getUsername());
        return ApiResponse.onSuccess(response);
    }

    @Operation(
            summary = "메시지 읽음 상태 변경API",
            description = "메시지를 읽었을 경우 변동이 되는 API입니다. WS 진행을 하면서 동시에 화면을 확인 했을 경우 update가 되면 됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PatchMapping("/message/update-read-status/{chatRoomId}")
    public ApiResponse<String> updateReadStatus(@PathVariable Long chatRoomId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        chatService.updateMessageReadStatus(userDetails.getUsername(),chatRoomId);

        return ApiResponse.onSuccess("메시지를 확인했습니다");
    }


    @Operation(
            summary = "채팅방 사용자 초대 API",
            description = "그룹 채팅일 때 추가적으로 사용자를 초대할 때 사용되는 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PostMapping("/invite/{chatRoomId}")
    public ApiResponse<ChatDTO.UserInviteResp> userInvite(@RequestBody @Valid ChatDTO.UserInviteDTO request,
                                                         @PathVariable Long chatRoomId,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        ChatDTO.UserInviteResp response = chatService.inviteUser(request, userDetails.getUsername(), chatRoomId);

        return ApiResponse.onSuccess(response);

    }


    @Operation(
            summary = "그룹 채팅 나가기API",
            description = "그룹 채팅에서 나가는 경우입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @PatchMapping("/leave/{chatRoomId}")
    public ApiResponse<ChatDTO.UserLeaveResp> leaveChatRoom(@PathVariable Long chatRoomId,
                                                            @AuthenticationPrincipal UserDetails userDetails) {

        ChatDTO.UserLeaveResp response = chatService.leaveUser(userDetails.getUsername(), chatRoomId);

        return ApiResponse.onSuccess(response);

    }

    @Operation(
            summary = "메시지 조회 API",
            description = "채팅방에 있는 메세지를 조회하는 API입니다. 채팅방에 있는 모든 메세지를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = com.example.instaclone_9room.apiPayload.ApiResponse.class)))

    })
    @GetMapping("/{chatRoomId}")
    public ApiResponse<ChatDTO.MessageListDTO> getMessageList(@PathVariable Long chatRoomId,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {

        ChatDTO.MessageListDTO response = chatService.getMessageList(userDetails.getUsername(), chatRoomId);

        return ApiResponse.onSuccess(response);

    }

}