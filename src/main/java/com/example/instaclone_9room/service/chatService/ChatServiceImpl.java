package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.converter.ChatConverter;
import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.ChatRoom;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.repository.ChatPartRepository;
import com.example.instaclone_9room.repository.ChatRoomRepository;
import com.example.instaclone_9room.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatPartRepository chatPartRepository;



}
