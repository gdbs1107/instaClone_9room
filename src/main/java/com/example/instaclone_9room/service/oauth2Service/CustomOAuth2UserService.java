package com.example.instaclone_9room.service.oauth2Service;

import com.example.instaclone_9room.controller.dto.CustomOAuth2User;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.controller.dto.response.GoogleResponse;
import com.example.instaclone_9room.controller.dto.response.OAuth2Response;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //OAuth2UserRequest 가 리소스 서버에서 전달하는 유저 정보를 다음 요청

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        //플랫폼을 구별 할 수 있는 Id
        String registrationId = userRequest.getClientRegistration().getRegistrationId();




        OAuth2Response oAuth2Response =null;



        if (registrationId.equals("google")){

            oAuth2Response=new GoogleResponse(oAuth2User.getAttributes());

        }
        else {
            return null;
        }



        //리소스 서버에서 넘기는 username은 겹칠 수 있기 때문에 우리만의 username을 설정해줌
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        UserEntity existData=userRepository.getByUsername(username);

        if(existData==null){

            UserEntity newUser = UserEntity.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .password("tempPassword")
                    .role("ROLE_USER")
                    .build();

            userRepository.save(newUser);


            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .role("ROLE_USER")
                    .build();

            return new CustomOAuth2User(userDTO);

        }else {


            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = UserDTO.builder()
                    .username(existData.getUsername())
                    .name(oAuth2Response.getName())
                    .role(existData.getRole())
                    .build();

            return new CustomOAuth2User(userDTO);

        }

    }
}
