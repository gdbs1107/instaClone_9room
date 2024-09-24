package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReelsService {
    void save(ReelsDTO.ReelsRequestDTO request, String username);

    ReelsDTO.ReelsResponseDTO getReels(Long reelsId);

    ReelsDTO.ReelsResponseDTO getReelsByPage(String username, int page);

    void updateReels(String username, Long reelsId, ReelsDTO.ReelsUpdateRequestDTO request);

    void deleteReels(String username, Long reelsId);
}
