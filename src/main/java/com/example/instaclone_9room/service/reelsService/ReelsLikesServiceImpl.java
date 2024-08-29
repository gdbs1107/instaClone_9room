package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsLikes;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsLikesRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReelsLikesServiceImpl implements ReelsLikesService {



    private final ReelsLikesRepository reelsLikesRepository;
    private final UserRepository userRepository;
    private final ReelsRepository reelsRepository;



    @Override
    public void toggleReelsLike(String username, Long reelsId){

        UserEntity findUser = findUser(username);

        Reels reels=findUser.getReels().stream()
                .filter(f->f.getId().equals(reelsId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Reels not found"));


        ReelsLikes reelsLikes=reelsLikesRepository.findByUserEntityAndReels(findUser,reels)
                .orElse(null);

        if(reelsLikes!=null){

            reels.minusReelsLikeCount();
            reelsLikesRepository.delete(reelsLikes);

        }else {

            ReelsLikes newReelsLikes = ReelsLikes.builder()
                    .userEntity(findUser)
                    .reels(reels)
                    .build();

            reels.addReelsLikeCount();

            reelsLikesRepository.save(newReelsLikes);

        }

    }





    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
