package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.controller.dto.ChatDTO;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {

    ChatDTO.ChatRoomCreateResp chatRoomCreate(ChatDTO.ChatRoomCreateDTO request, String userName);

    void chatRoomDelete(Long chatRoomId, String userName);

    void updateChatRoomName(ChatDTO.ChatRoomNameUpdateDTO request, Long chatRoomId, String userName);

    ChatDTO.ChatRoomListResponse getChatRoomSummaryList(String userName);

    // ------ 세션 사용 ----- //

    void saveMessage(ChatDTO.MessageDTO messageDTO);


}
