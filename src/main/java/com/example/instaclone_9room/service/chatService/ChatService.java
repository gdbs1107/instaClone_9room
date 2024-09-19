package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.domain.enumPackage.ReadStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatService {

    ChatDTO.ChatRoomCreateResp chatRoomCreate(ChatDTO.ChatRoomCreateDTO request, String userName);

    void chatRoomDelete(Long chatRoomId, String userName);

    ChatDTO.ChatRoomNameUpdateResp updateChatRoomName(ChatDTO.ChatRoomNameUpdateDTO request, Long chatRoomId, String userName);

    ChatDTO.ChatRoomListResponse getChatRoomSummaryList(String userName);

    void updateMessageReadStatus(Long chatRoomId, Long userId, ReadStatus readStatus);

    // ------ 세션 사용 ----- //

    void saveMessage(ChatDTO.MessageDTO messageDTO);





}
