package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.apiPayload.code.BaseErrorCode;
import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.GeneralException;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.converter.UserConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;
import com.example.instaclone_9room.jwt.JwtUtil;
import com.example.instaclone_9room.repository.RefreshRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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


    @Override
    public void updateUser(UserDTO.UserUpdateRequestDTO request, String username){

        UserEntity user = findUser(username);
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
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("refresh expired token");
        }

        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            throw new RuntimeException("refresh Token null");
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

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .link(joinDto.getLink())
                .name(joinDto.getName())
                .gender(gender)
                .birthday(joinDto.getBirthday())
                .onPrivate(true)
                .followerCount(0)
                .followCount(0)
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




    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }



}
