package com.example.instaclone_9room.service;


import com.example.instaclone_9room.controller.dto.CustomUserDetails;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity =userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if(userEntity!=null){
            return new CustomUserDetails(userEntity);
        }


        return null;
    }
}