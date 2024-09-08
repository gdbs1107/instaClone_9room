package com.example.instaclone_9room.service.memoService;

import com.example.instaclone_9room.controller.dto.MemoDTO;

public interface MemoService {

    void create(MemoDTO.MemoCreateDTO request, String userName);

    void delete(String userName, Long memoId);

    void update(String userName, Long memoId, MemoDTO.MemoUpdateDTO request);

    MemoDTO.MemoListResponseDTO getMemoList(String userName, int page);

}
