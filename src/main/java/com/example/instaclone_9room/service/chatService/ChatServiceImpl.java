package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.repository.ChatPartRepository;
import com.example.instaclone_9room.repository.ChatRoomRepository;


import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatPartRepository chatPartRepository;



}
