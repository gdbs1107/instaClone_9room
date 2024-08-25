package com.example.instaclone_9room.controller.dto.chatRoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChatRoomRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomCreateRequest {
        // 채팅방 만들어지는 요청
        private Long creatorId;
        private Long targetUserId;
        private String roomName;
        private List<Long> participantIds;

        public String determineRoomName(String targetUserName) {

            if (targetUserId != null && participantIds == null) {
                return targetUserName;
            }

            if (roomName != null && !roomName.isEmpty()) {
                return roomName;
            }

            throw new IllegalArgumentException("Group chat must have a room name.");
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomDeleteRequest {
        // 채팅방 삭제 요청
        private Long roomId;
        private Long requesterId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomInfoRequest {
        // 채팅방 상세 정보 요청
        private Long roomId;
        private Long requesterId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class ChatRoomListRequest {
        // 채팅방 목록 정보 요청
        private Long userId;
    }
}
