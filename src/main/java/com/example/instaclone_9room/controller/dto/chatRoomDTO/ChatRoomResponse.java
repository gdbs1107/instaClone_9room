package com.example.instaclone_9room.controller.dto.chatRoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChatRoomResponse {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomCreateResponse {
        // 채팅방 만들고 응답
        private Long chatRoomId;
        private Long creatorId;
        private String roomName;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomDeleteResponse {
        // 채팅방 삭제 후 응답
        private Long roomId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomInfoResponse {
        // 채팅방 전체 정보 응답
        private Long roomId;
        private String roomName;
        private List<Long> participantIds;
        private LocalDateTime createdAt;
        private String lastMessage;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomSummaryDTO {
        // 채팅방 요약 정보 -> 채팅방 목록(List) 요청 시 같이 갈 정보
        private Long chatRoomId;
        private String roomName;
        private String lastMessage;
        private int participantCount;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomListResponse {
        // 채팅방 목록 정보 요청
        private List<ChatRoomSummaryDTO> chatRooms;
    }

}
