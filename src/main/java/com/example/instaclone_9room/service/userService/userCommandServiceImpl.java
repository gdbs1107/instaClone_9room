package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.TokenCategoryHandler;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.converter.UserConverter;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;
import com.example.instaclone_9room.jwt.JwtUtil;
import com.example.instaclone_9room.repository.RefreshRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.repository.followRepository.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class userCommandServiceImpl implements UserCommandService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FollowerRepository followerRepository;


    @Override
    public void updateUser(UserDTO.UserUpdateRequestDTO request, String username){

        UserEntity user = findUser(username);


        if (!username.equals(user.getUsername())) {
            throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
        }


        Gender gender = UserConverter.toGender(request.getGenderType());

        System.out.println("name="+request.getName());


        //글자길이 넘어가면 오류 반환
        if (request.getName().length()>20 && request.getIntroduction().length()>30 && request.getLink().length()>30){

            throw new MemberCategoryHandler(ErrorStatus.TOO_LONG_REQUEST);

        }


        user.setInfo(request.getName(),
                gender,
                request.getBirthday(),
                request.getLink(),
                request.getIntroduction(),
                request.getOnPrivate());

        userRepository.save(user);
    }


    @Override
    public void deleteUser(String username){

        UserEntity user = findUser(username);
        userRepository.delete(user);
    }




    @Override
    public void logout(String refreshToken) {
        if (refreshToken == null) {
            throw new TokenCategoryHandler(ErrorStatus.TOKEN_NOT_INCORRECT);
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (Exception e) {
            throw new TokenCategoryHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            throw new TokenCategoryHandler(ErrorStatus.TOKEN_NULL);
        }

        String username = jwtUtil.getUsername(refreshToken);

        refreshRepository.deleteByRefresh(refreshToken);
    }




    @Override
    public void joinProcess(JoinDto.JoinRequestDTO joinDto){

        String username=joinDto.getUsername();
        System.out.println(username);

        String password= joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);



        if (isExist){
            throw new MemberCategoryHandler(ErrorStatus.USERNAME_EXISTED);
        }

        if (joinDto.getName().length()>20  && joinDto.getLink().length()>30) {
            throw new MemberCategoryHandler(ErrorStatus.TOO_LONG_REQUEST);
        }

        if(joinDto.getGenderType()!=1 && joinDto.getGenderType()!=2){
            throw new MemberCategoryHandler(ErrorStatus.GENDER_ERROR);
        }



        Gender gender = UserConverter.toGender(joinDto.getGenderType());

        //일반로그인에서는 이메일을 요구하지 않기 때문에 temp 기입
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .link(joinDto.getLink())
                .name(joinDto.getName())
                .gender(gender)
                .birthday(joinDto.getBirthday())
                .onPrivate(false)
                .followerCount(0)
                .followCount(0)
                .email("temp@temp.com")
                .build();

        userRepository.save(userEntity);
    }


    @Override
    public UserDTO.UserGetResponseDTO getUserProfile(String username){

        UserEntity user = findUser(username);
        return UserDTO.UserGetResponseDTO.builder()
                .introduction(user.getIntroduction())
                .link(user.getLink())
                .name(user.getName())
                .onPrivate(user.getOnPrivate())
                .genderType(user.getGender())
                .build();
    }

    @Override
    public UserDTO.UserGetHomeResponseDTO userGetHomeProfile(String username){

        UserEntity user = findUser(username);
        return UserConverter.toUserGetHomeResponseDTO(user);
    }



    @Override
    public UserDTO.UserGetResponseDTO getUserProfileByUsername(String targetUsername, String requestingUsername) {

        // 타겟 사용자와 요청 사용자 조회
        UserEntity targetUser = findUser(targetUsername);
        UserEntity requestingUser = findUser(requestingUsername);

        // 요청 사용자가 타겟 사용자의 팔로워인지 확인하는 쿼리
        boolean isFollower = followerRepository.existsByUserEntityAndFollowerUser(targetUser, requestingUser);

        // 비공개 상태 확인
        if (targetUser.getOnPrivate() && !isFollower) {
            throw new MemberCategoryHandler(ErrorStatus.MEMBER_PRIVATE_ERROR);
        }

        // 프로필 정보 반환
        return UserDTO.UserGetResponseDTO.builder()
                .introduction(targetUser.getIntroduction())
                .link(targetUser.getLink())
                .name(targetUser.getName())
                .onPrivate(targetUser.getOnPrivate())
                .genderType(targetUser.getGender())
                .build();
    }


    @Override
    public void setUserInfo(UserDTO.UserSetInfo request, String username){
        UserEntity user = findUser(username);

        Gender gender = UserConverter.toGender(request.getGenderType());

        user.setUserInfo(
                false,
                null,
                0,
                0,
                gender,
                request.getBirthday(),
                request.getLink()
        );

        userRepository.save(user);
    }





    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }



}
