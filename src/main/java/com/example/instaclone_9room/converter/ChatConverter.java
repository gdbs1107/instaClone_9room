package com.example.instaclone_9room.converter;


import com.example.instaclone_9room.controller.dto.ChatDTO;
import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.Message;
import com.example.instaclone_9room.domain.enumPackage.MessageStatus;

public class ChatConverter {

    public static Message toMessage(ChatDTO.MessageDTO messageDTO, ChatPart chatPart) {
        return Message.builder()
                .content(messageDTO.getMessage())
                .messageStatus(MessageStatus.SENT)
                .chatPart(chatPart)
                .build();
    }
}
