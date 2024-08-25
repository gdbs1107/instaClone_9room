package com.example.instaclone_9room.controller.dto.messageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public class MessageResponseDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageReceiveResponseDTO {
        // 메시지 수신 응답
        private Long messageId;     // 수신 메시지 ID
        private Long senderId;      // 보낸 사람 ID
        private Long receiverId;    // 받은 사람 ID
        private String content;     // 메시지 내용
        private LocalDateTime timestamp;    // 메시지 보낸 시간

        @Nullable
        private String imageURL;    // 이미지 URL
        @Nullable
        private String emojiCode;   // 이모티콘 코드


    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageListResponseDTO {
        // 메시지 목록 응답
        private Long chatRoomId;    // 메시지 목록을 조회할 채팅방 ID
        private List<MessageSummary> messages;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageDeleteResponseDTO {
        // 메시지 삭제 응답
        private Long messageId;     // 삭제할 메시지 ID
        private boolean success;    // 삭제 성공 여부
        private LocalDateTime deletedAt;    // 삭제 시간
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MessageSummary {
        // 메시지 요약 정보

        private LocalDateTime groupedTime;
        private List<MessageDetail> messageDetails;

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Builder
        public static class MessageDetail {
            private Long messageId;     // 수신 메시지 ID
            private Long senderId;      // 보낸 사람 ID
            private Long receiverId;    // 받은 사람 ID
            private String content;     // 메시지 내용
            private LocalDateTime timestamp;    // 메시지 보낸 시간

            @Nullable
            private String imageURL;    // 이미지 URL
            @Nullable
            private String emojiCode;   // 이모티콘 코드
        }

    }

}
