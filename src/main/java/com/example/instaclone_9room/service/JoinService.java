package com.example.instaclone_9room.service;


import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto.JoinRequestDTO joinDto){

        String username=joinDto.getUsername();
        System.out.println(username);

        String password= joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist){
            System.out.println("이미 존재하는 회원입니다");
            return;
        }


        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        userRepository.save(userEntity);
    }
}
