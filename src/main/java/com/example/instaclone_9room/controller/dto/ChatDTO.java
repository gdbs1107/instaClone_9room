package com.example.instaclone_9room.controller.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ChatDTO {

    // 채팅방 초기에 만들 때
    @Getter
    public static class ChatRoomCreateDTO {

        private List<String> invitedUserNames;

    }

    // 채팅방에 사용자 초대
    @Getter
    public static class UserInviteDTO {

        private List<String> invitedUserNames;

    }


    // 채팅방 이름 바꿀 때 (1:1 채팅방은 불가)
    @Getter
    public static class ChatRoomNameUpdateDTO {

        private String newChatRoomName;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageDTO {
        private Long messageId;
        private Long chatRoomId;
        private Long senderId;
        private String content;
        private LocalDateTime timeStamp;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageListDTO {

        private List<MessageDTO> messages;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomSummaryDTO {

        private String displayName;    // 채팅방 이름이 상대방 사용자 이름
        private String lastMessage;
        private LocalDateTime lastMessageTime;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomListResponse {

        private List<ChatRoomSummaryDTO> chatRoomSummaryDTOS;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class SocketMessageDTO {

        public enum MessageType {
            ENTER, CHAT, LEAVE;

        }

        private String message;
        private MessageType messageType;
        private Long chatRoomId;
        private Long senderId;
    }




    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomCreateResp {
        private Long chatRoomId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomDeleteResp {
        private Long deletedChatRoomId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomNameUpdateResp {
        private Long chatRoomId;
        private String newChatRoomName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class UserInviteResp {

        private List<Long> invitedUserId;

        private Long inviterUserId;

        private Long chatRoomId;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class UserLeaveResp {

        private Long leaveUserId;

        private Long chatRoomId;
    }



}
