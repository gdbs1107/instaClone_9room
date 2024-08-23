package com.example.instaclone_9room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MessageDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class SendMessageReq {

        private String content; // 메시지 내용
        private Long senderId; // 보낸 사람
        private Long chatRoomId; // 채팅방
        private String image;   // 사진 (선택적)
        private LocalDateTime sentAt; // 보낸 시점
        private Long replyToMessage; // 답장 보낼 메세지 고르기 (선택적)
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class SendMessageResp {
        private Long messageId; // 전송된 메세지 Id
        private LocalDateTime sentAt; // 언제 보냈는지
        private Long chatRoomId;    // 어느 채팅방에 보냈는지
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class DeleteMessageReq {
        private Long messageId; // 삭제할 메시지 id
        private Long chatRoomId; // 삭제할 메시지가 속한 채팅방 id
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class DeleteRoomReq {
        private Long chatRoomId;    // 삭제할 채팅방 id
    }

}
