package com.example.instaclone_9room.converter;


import com.example.instaclone_9room.controller.dto.MemoDTO;
import com.example.instaclone_9room.domain.Memo;
import com.example.instaclone_9room.domain.userEntity.UserEntity;


import java.util.List;
import java.util.stream.Collectors;

public class MemoConverter {
    // MemoCreateDTO -> Memo (엔티티 변환)
    public static Memo toMemo(MemoDTO.MemoCreateDTO request, UserEntity user) {
        return Memo.builder()
                .userEntity(user)
                .content(request.getContent())
                .build();
    }


    // Memo -> MemoSummaryDTO (엔티티 변환)
    public static MemoDTO.MemoSummaryDTO toMemoSummaryDTO(Memo memo) {
        return MemoDTO.MemoSummaryDTO.builder()
                .userName(memo.getUserEntity().getName())
                .content(memo.getContent())
                .createdAt(memo.getCreatedAt())
                .build();
    }

    // List<Memo> -> MemoListDTO (엔티티 변환)
    public static MemoDTO.MemoListResponseDTO toMemoListDTO(List<MemoDTO.MemoSummaryDTO> memos, int totalPages, long totalElements) {
//        List<MemoDTO.MemoSummaryDTO> memoSummaryDTOS = memos.stream()
//                .map(MemoConverter::toMemoSummaryDTO)
//                .collect(Collectors.toList());

        return MemoDTO.MemoListResponseDTO.builder()
                .memoSummaryDTOS(memos)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    /// ---------- Response ----------- //

    public static MemoDTO.MemoCreateResp toMemoCreateResp(Memo memo) {
        return MemoDTO.MemoCreateResp.builder()
                .memoId(memo.getId())
                .build();
    }

}
