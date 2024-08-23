package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.MessageDTO;
import com.example.instaclone_9room.domain.Message;

import java.time.LocalDateTime;

public class MessageConverter {

    // Entity -> DTO
    public static MessageDTO.SendMessageResp toSendMessageResp(Message message) {

        return MessageDTO.SendMessageResp.builder()
                .chatRoomId(message.getChatPart().getChatRoom().getId())
                .messageId(message.getId())
                .sentAt(message.getCreatedAt())
                .build();
    }


}
