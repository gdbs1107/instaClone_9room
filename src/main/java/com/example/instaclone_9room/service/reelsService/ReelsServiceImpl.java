package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.UnauthorizedException;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.ReelsCategoryHandler;
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

    @Override
    public ReelsDTO.ReelsResponseDTO getReels(Long reelsId){

        Reels findReels = findReels(reelsId);
        UserEntity userEntity = findReels.getUserEntity();

        return ReelsConverter.toReelsResponseDTO(findReels, userEntity);

    }


    @Override
    public void updateReels(String username, Long reelsId, ReelsDTO.ReelsUpdateRequestDTO request){

        UserEntity findUser = findUser(username);
        Reels findReels = findReels(reelsId);

        UserEntity userEntity = findReels.getUserEntity();

        if(findUser.equals(userEntity)){

            findReels.update(request.getContent(),
                    request.getAudioPath(),
                    request.getAudioName());

            reelsRepository.save(findReels);
        }else {
            throw new UnauthorizedException("니꺼 아니잖아");
        }
    }


    @Override
    public void deleteReels(String username, Long reelsId){

        UserEntity findUser = findUser(username);
        Reels findReels = findReels(reelsId);

        UserEntity userEntity = findReels.getUserEntity();

        if(findUser.equals(userEntity)){

            reelsRepository.delete(findReels);
        }else {
            throw new UnauthorizedException("니꺼 아니잖아");
        }
    }



    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }

    private Reels findReels(Long id) {
        return reelsRepository.findById(id)
                .orElseThrow(() -> new ReelsCategoryHandler(ErrorStatus.REELS_NOT_FOUND));
    }
}
