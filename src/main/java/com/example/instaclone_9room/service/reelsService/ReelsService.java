package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReelsService {
    void save(ReelsDTO.ReelsRequestDTO request, String username);
}
