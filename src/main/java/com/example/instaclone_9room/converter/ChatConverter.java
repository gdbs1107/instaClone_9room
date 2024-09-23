package com.example.instaclone_9room.converter;


import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.Message;
import com.example.instaclone_9room.domain.enumPackage.MessageStatus;

public class ChatConverter {

    public static Message toSocketMessage(ChatDTO.SocketMessageDTO socketMessageDTO, ChatPart chatPart) {
        return Message.builder()
                .content(socketMessageDTO.getMessage())
                .messageStatus(MessageStatus.SENT)
                .chatPart(chatPart)
                .build();
    }


    public static ChatDTO.MessageDTO convertToMessageDTO(Message message) {
        return ChatDTO.MessageDTO.builder()
                .messageId(message.getId())
                .chatRoomId(message.getChatPart().getChatRoom().getId())
                .senderId(message.getChatPart().getUserEntity().getId())
                .content(message.getContent())
                .timeStamp(message.getUpdatedAt())
                .build();
    }
}
