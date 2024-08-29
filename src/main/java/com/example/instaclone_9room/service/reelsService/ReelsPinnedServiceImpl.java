package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.converter.ReelsConverter;
import com.example.instaclone_9room.converter.ReelsPinnedConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsPinned;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsPinnedRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReelsPinnedServiceImpl implements ReelsPinnedService {

    private final ReelsRepository reelsRepository;
    private final UserRepository userRepository;
    private final ReelsPinnedRepository reelsPinnedRepository;



    @Override
    public void savePinnedReels(String username, Long reelsId){

        UserEntity findUser = findUser(username);
        Reels findReels = findReels(reelsId);

        ReelsPinned byUserEntityAndReels = reelsPinnedRepository.findByUserEntityAndReels(findUser, findReels)
                .orElse(null);

        if (byUserEntityAndReels!=null){

            findReels.minusReelsPinnedCount();
            reelsPinnedRepository.delete(byUserEntityAndReels);

        }else {
            ReelsPinned reelsPinned = ReelsPinnedConverter.toReelsPinned(findUser, findReels);
            findReels.addReelsPinnedCount();
            reelsPinnedRepository.save(reelsPinned);
        }


    }


    @Override
    public List<ReelsDTO.ReelsResponseDTO> getReelsPinned(String username) {

        UserEntity user = findUser(username); // 요청한 유저 정보를 가져옴
        List<ReelsPinned> pinnedReelsList = reelsPinnedRepository.findByUserEntity(user);


        List<ReelsDTO.ReelsResponseDTO> responseDTOList = pinnedReelsList.stream()
                .map(pinnedReels -> {
                    Reels reels = pinnedReels.getReels();
                    UserEntity reelsUser = reels.getUserEntity();
                    return ReelsConverter.toReelsResponseDTO(reels, reelsUser);
                })
                .collect(Collectors.toList());

        return responseDTOList;
    }




    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Reels findReels(Long id) {
        return reelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reels not found"));
    }
}
