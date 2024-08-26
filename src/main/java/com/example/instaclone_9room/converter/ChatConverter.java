package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.chatRoomDTO.ChatRoomRequest;
import com.example.instaclone_9room.domain.ChatPart;
import com.example.instaclone_9room.domain.ChatRoom;
import com.example.instaclone_9room.domain.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatConverter {

    public static ChatRoom toChatRoom(ChatRoomRequest.ChatRoomCreateRequest request, UserEntity targetUser) {
        String roomName = request.determineRoomName(targetUser.getUsername());

        return ChatRoom.builder()
                .roomName(roomName)
                .build();
    }

    public static ChatPart toChatPart(UserEntity user, ChatRoom chatRoom) {
        return ChatPart.builder()
                .userEntity(user)
                .chatRoom(chatRoom)
                .build();
    }
}
