package com.example.instaclone_9room.service.chatService;

import com.example.instaclone_9room.controller.dto.chatRoomDTO.ChatRoomRequest;
import com.example.instaclone_9room.controller.dto.chatRoomDTO.ChatRoomResponse;
import com.example.instaclone_9room.converter.ChatConverter;
import com.example.instaclone_9room.domain.ChatPart;
import com.example.instaclone_9room.domain.ChatRoom;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.repository.ChatPartRepository;
import com.example.instaclone_9room.repository.ChatRoomRepository;
import com.example.instaclone_9room.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatPartRepository chatPartRepository;
    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;


    @Override
    public void create(ChatRoomRequest.ChatRoomCreateRequest request, String creatorName, String targetName) {
        UserEntity createUser = userRepository.findByUsername(creatorName).orElseThrow(
                () -> new RuntimeException("user not found"));

        UserEntity tagetUser = userRepository.findByUsername(targetName).orElseThrow(
                () -> new RuntimeException("user not found"));

        ChatRoom newChatRoom = ChatConverter.toChatRoom(request, tagetUser);

        chatRoomRepository.save(newChatRoom);

        ChatPart newChatPartCreateUser = ChatConverter.toChatPart(createUser, newChatRoom);
        ChatPart newChatPartTargetUser = ChatConverter.toChatPart(tagetUser, newChatRoom);

        chatPartRepository.save(newChatPartCreateUser);
        chatPartRepository.save(newChatPartTargetUser);
    }

    @Override
    public void delete(Long chatRoomId, String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));

        ChatRoom exisitingChatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("chatroom not found"));

        List<ChatPart> chatParts = chatPartRepository.findByChatRoom(exisitingChatRoom);

        chatPartRepository.deleteAll(chatParts);
        chatRoomRepository.delete(exisitingChatRoom);
    }

    @Override
    public ChatRoomResponse.ChatRoomListResponse viewAllChatRoom(Long userId) {
    }
}
