package com.example.instaclone_9room.service.impl;

import com.example.instaclone_9room.controller.dto.MessageDTO;
import com.example.instaclone_9room.domain.Message;
import com.example.instaclone_9room.repository.MessageRepository;
import com.example.instaclone_9room.service.MessageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageCommandServiceImpl implements MessageCommandService {

    private final MessageRepository messageRepository;

    @Override
    public Message sendMessage(MessageDTO.SendMessageReq request) {

        return null;
    }
}
