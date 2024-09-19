package com.example.instaclone_9room.converter;


import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.Message;
import com.example.instaclone_9room.domain.enumPackage.MessageStatus;

public class ChatConverter {

//    public static ChatDTO.ChatRoomCreateDTO toChatPart(UserEntity user) {
//
//    }

//    public static ChatDTO.ChatRoomListResponse toChatListResponseDTO(UserEntity user) {
//
//        List<Long> userChatRoomIds = user.getChatParts().stream()
//                .map(chatPart -> ChatDTO.ChatRoomSummaryDTO.builder()
//                        .chatRoomName(chatPart.getChatRoom().getRoomName())
//                        .lastMessage(chatPart.getMessageList().stream()))
//                        .lastMessageTime()


    public static Message toMessage(ChatDTO.MessageDTO messageDTO, ChatPart chatPart) {
        return Message.builder()
                .content(messageDTO.getMessage())
                .messageStatus(MessageStatus.SENT)
                .chatPart(chatPart)
                .build();
    }
}
