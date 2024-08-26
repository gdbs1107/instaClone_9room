package com.example.instaclone_9room.service.chatService;

import com.example.instaclone_9room.controller.dto.chatRoomDTO.ChatRoomRequest;
import com.example.instaclone_9room.controller.dto.chatRoomDTO.ChatRoomResponse;
import com.example.instaclone_9room.domain.ChatRoom;

public interface ChatService {

    // 채팅방 생성
    void create(ChatRoomRequest.ChatRoomCreateRequest request,String creatorName, String targetName);

    // 채팅방 삭제
    void delete(Long chatRoomId, String username);

    // 채팅방 목록 조회
    ChatRoomResponse.ChatRoomListResponse viewAllChatRoom(Long chatRoomId);
}
