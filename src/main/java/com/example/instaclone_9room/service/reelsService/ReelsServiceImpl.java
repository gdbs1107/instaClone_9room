package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.ImageCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.ReelsCategoryHandler;
import com.example.instaclone_9room.controller.dto.ReelsDTO;
import com.example.instaclone_9room.converter.ReelsConverter;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.userEntity.UserProfileImage;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        UserProfileImage findImage = getFindImage(userEntity);

        return ReelsConverter.toReelsResponseDTO(findReels, userEntity, findImage);

    }



    @Override
    public ReelsDTO.ReelsResponseDTO getReelsByPage(String username, int page) {

        UserEntity user = findUser(username);
        UserProfileImage findImage = getFindImage(user);


        Page<Reels> reelsPage = reelsRepository.findAllByUserEntity_Id(
                user.getId(),
                PageRequest.of(page, 1) // 한 페이지에 하나의 릴스만 반환
        );

        if (reelsPage.hasContent()) {
            Reels reels = reelsPage.getContent().get(0); // 한 페이지에 하나만 있으므로 첫 번째 항목을 가져옴
            return new ReelsConverter().toReelsResponseDTO(reels, user,findImage);
        } else {
            throw new ReelsCategoryHandler(ErrorStatus.REELS_END);
        }
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
            throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
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
            throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
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

    private static UserProfileImage getFindImage(UserEntity user) {
        return user.getUserProfileImages().stream()
                .findFirst().orElseThrow(() -> new ImageCategoryHandler(ErrorStatus.IMAGE_NOT_FOUND));
    }
}
