package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.controller.dto.ChatDTO;

public interface ChatService {

    ChatDTO.ChatRoomCreateResp chatRoomCreate(ChatDTO.ChatRoomCreateDTO request, String userName);

    void chatRoomDelete(Long chatRoomId, String userName);

    ChatDTO.ChatRoomNameUpdateResp setChatRoomName(ChatDTO.ChatRoomNameUpdateDTO request, Long chatRoomId, String userName);

    ChatDTO.ChatRoomListResponse getChatRoomSummaryList(String userName);

    void updateMessageReadStatus(String userName, Long chatRoomId);

    ChatDTO.UserInviteResp inviteUser(ChatDTO.UserInviteDTO request, String userName, Long chatRoomId);

    ChatDTO.UserLeaveResp leaveUser(String userName, Long chatRoomId);


    // ------ 세션 사용 ----- //

    void saveMessage(ChatDTO.MessageDTO messageDTO);







}
