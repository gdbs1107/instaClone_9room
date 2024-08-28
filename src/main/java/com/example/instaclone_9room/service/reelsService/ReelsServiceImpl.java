package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.converter.ReelsConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReelsServiceImpl implements ReelsService {

    private final ReelsRepository reelsRepository;
    private final UserRepository userRepository;

    @Override
    public void save(ReelsDTO.ReelsRequestDTO request, String username){
        UserEntity findUser = findUser(username);

        Reels reels = ReelsConverter.toReels(request, findUser);
        reelsRepository.save(reels);
    }




    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
