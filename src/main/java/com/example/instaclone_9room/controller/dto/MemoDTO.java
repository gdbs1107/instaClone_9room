package com.example.instaclone_9room.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MemoDTO {

    @Getter
    public static class MemoCreateDTO {
        // 메모 작성 요청

        private String content;

    }

    @Getter
    public static class MemoDeleteDTO {
        // 메모 삭제 요청

        private Long memoId;
    }

    @Getter
    public static class MemoUpdateDTO {
        // 메모 재작성 요청

        private Long memoId;
        private String newContent;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MemoSummaryDTO {
        // 메모 정보
        private String userName;
        private String content;
        private LocalDateTime createdAt;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MemoListResponseDTO {
        // 메모 전체 조회 때 사용
        private List<MemoSummaryDTO> memoSummaryDTOS;
        private Integer totalPages;
        private Long totalElements;

    }

}
