package com.example.instaclone_9room.controller.dto.messageDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

public class MessageRequestDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageSendRequestDTO {
        // 메시지 보내는 요청

        private Long senderId;  // 보내는 사람 ID
        private Long receiverId;    // 받는 사람 ID
        private String content;     // 메시지 내용

        @Nullable
        private String imageURL;
        @Nullable
        private String emojiCod;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageDeleteRequestDTO {
        // 메시지 삭제 요청
        private Long messageId;     // 삭제할 메세지 ID
        private Long requesterId;   // 삭제 요청자 ID
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageListRequestDTO {
        // 메시지 목록 조회
        private Long chatRoomId;    // 조회할 채팅방 ID
        private Long requesterId;   // 요청자 ID
    }
}
