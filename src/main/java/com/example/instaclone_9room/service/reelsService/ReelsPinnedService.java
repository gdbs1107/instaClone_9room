package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReelsPinnedService {
    void savePinnedReels(String username, Long reelsId);

    List<ReelsDTO.ReelsResponseDTO> getReelsPinned(String username);
}
