package com.example.instaclone_9room.service.chatService;


import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.ChatCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.controller.dto.ChatDTO;

import com.example.instaclone_9room.converter.ChatConverter;
import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.ChatRoom;
import com.example.instaclone_9room.domain.DM.Message;
import com.example.instaclone_9room.domain.enumPackage.MessageStatus;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.repository.DmRepository.ChatPartRepository;


import com.example.instaclone_9room.repository.DmRepository.ChatRoomRepository;
import com.example.instaclone_9room.repository.DmRepository.MessageRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatPartRepository chatPartRepository;
    private final MessageRepository messageRepository;


    @Override
    public ChatDTO.ChatRoomCreateResp chatRoomCreate(ChatDTO.ChatRoomCreateDTO request, String userName) {

        ChatRoom chatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(chatRoom);

        List<String> allUsers = new ArrayList<>(request.getInvitedUserNames());
        allUsers.add(userName);

        List<String> sortedAllUsers = allUsers.stream()
                .sorted()
                .toList();

        List<ChatPart> chatParts = new ArrayList<>();

        for (String user : sortedAllUsers) {
            UserEntity userEntity = findUser(user);

            List<String> otherUsers = new ArrayList<>(allUsers);
            otherUsers.remove(user);

            String chatRoomName = String.join(", ", otherUsers);

            ChatPart chatPart = ChatPart.builder()
                    .userEntity(userEntity)
                    .chatRoom(chatRoom)
                    .chatRoomName(chatRoomName)
                    .build();

            chatParts.add(chatPart);
        }

        chatPartRepository.saveAll(chatParts);

        return new ChatDTO.ChatRoomCreateResp(chatRoom.getId());

    }


    @Override
    public ChatDTO.ChatRoomNameUpdateResp setChatRoomName(ChatDTO.ChatRoomNameUpdateDTO request, Long chatRoomId, String userName) {

        UserEntity findUser = findUser(userName);
        List<ChatPart> chatParts = chatPartRepository.findAllByChatRoomId(chatRoomId);

        boolean isAuthorized = chatParts.stream()
                .anyMatch(chatPart -> chatPart.getUserEntity().getId().equals(findUser.getId()));

        if (!isAuthorized) {
            throw new ChatCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
        } else {
            chatParts.forEach(chatPart -> chatPart.update(request.getNewChatRoomName()));
        }

        chatPartRepository.saveAll(chatParts);

        return new ChatDTO.ChatRoomNameUpdateResp(chatRoomId, request.getNewChatRoomName());

    }

    // QueryDSL 공부 후 해볼 것
    @Override
    public ChatDTO.ChatRoomListResponse getChatRoomSummaryList(String userName) {


        return null;
    }

    @Override
    public void updateMessageReadStatus(String userName, Long chatRoomId) {
        UserEntity findUser = findUser(userName);
        List<ChatPart> chatParts = chatPartRepository.findAllByChatRoomId(chatRoomId);

        List<ChatPart> otherChatParts = chatParts.stream()
                .filter(chatPart -> !chatPart.getUserEntity().equals(findUser))
                .toList();

        otherChatParts.forEach(
                chatPart -> {
                    List<Message> messages = messageRepository.findAllByChatPartId(chatPart.getId());


                    List<Message> unreadMessages = messages.stream()
                            .filter(message -> message.getMessageStatus() == MessageStatus.DELIVERED)
                            .toList();

                    if (unreadMessages.isEmpty()) {
                        System.out.println("이미 모든 메시지가 읽음 처리되었습니다.");
                        return;
                    }

                    unreadMessages.forEach(message -> message.updateStatus(MessageStatus.READ));

                    messageRepository.saveAll(unreadMessages);
                }
        );
    }

    @Override
    public ChatDTO.UserInviteResp inviteUser(ChatDTO.UserInviteDTO request, String userName, Long chatRoomId) {
        // 채팅방과 초대자를 찾음
        ChatRoom findChatRoom = findChatRoomById(chatRoomId);
        UserEntity findUser = findUser(userName);
        ChatPart findChatPart = findChatPartByChatRoomIdAndUserId(chatRoomId, findUser.getId());

        // 현재 채팅방에 있는 사용자들을 가져옴
        List<UserEntity> currentUsers = findChatRoom.getChatPartList().stream()
                .map(ChatPart::getUserEntity)
                .toList();

        // 초대된 사용자들을 찾음
        List<UserEntity> invitedUsers = request.getInvitedUserNames().stream()
                .map(this::findUser)
                .toList();

        // 초대된 사용자와 기존 사용자를 통합
        List<UserEntity> allUsers = new ArrayList<>(currentUsers);
        allUsers.addAll(invitedUsers);

        // 채팅방 이름 업데이트 필요 여부를 결정
        String defaultChatRoomName = currentUsers.stream()
                .map(UserEntity::getUsername)
                .sorted()
                .filter(name -> !name.equals(userName))
                .collect(Collectors.joining(", "));

        boolean isDefaultName = findChatPart.getChatRoomName().equals(defaultChatRoomName);

        if (isDefaultName) {
            // 기존 사용자들의 ChatPart를 업데이트하거나 새로 추가
            for (UserEntity user : allUsers) {
                String newChatRoomName = allUsers.stream()
                        .map(UserEntity::getUsername)
                        .sorted()
                        .filter(name -> !name.equals(user.getUsername()))
                        .collect(Collectors.joining(", "));

                if (currentUsers.contains(user)) {
                    ChatPart currentUserChatPart = findChatPartByChatRoomIdAndUserId(chatRoomId, user.getId());
                    currentUserChatPart.update(newChatRoomName);
                } else {
                    ChatPart chatPart = ChatPart.builder()
                            .userEntity(user)
                            .chatRoom(findChatRoom)
                            .chatRoomName(newChatRoomName)
                            .build();
                    chatPartRepository.save(chatPart);
                }
            }
        } else {
            // 기본값이 아닌 경우: 새로 초대된 사용자에 대한 ChatPart만 추가
            for (UserEntity user : invitedUsers) {
                if (!currentUsers.contains(user)) {
                    ChatPart chatPart = ChatPart.builder()
                            .userEntity(user)
                            .chatRoom(findChatRoom)
                            .chatRoomName(findChatPart.getChatRoomName()) // 기존 이름 사용
                            .build();
                    chatPartRepository.save(chatPart);
                }
            }
        }
        List<Long> invitedUserIds = invitedUsers.stream()
                .map(UserEntity::getId)
                .toList();

        return new ChatDTO.UserInviteResp(invitedUserIds,findUser.getId(),chatRoomId);
    }

    @Override
    public ChatDTO.UserLeaveResp leaveUser(String userName, Long chatRoomId) {
        UserEntity findUser = findUser(userName);
        ChatPart chatPart = findChatPartByChatRoomIdAndUserId(findUser.getId(), chatRoomId);

        chatPartRepository.delete(chatPart);

        return new ChatDTO.UserLeaveResp(findUser.getId(), chatRoomId);

    }


    @Override
    public void chatRoomDelete(Long chatRoomId, String userName) {

    }


    // -----------------------소켓 통신 사용 예정----------------------- //

    @Override
    public void saveMessage(ChatDTO.MessageDTO messageDTO) {
        // 로그 추가: chatRoomId와 senderId 확인
        log.info("Saving message with chatRoomId: {}, senderId: {}", messageDTO.getChatRoomId(), messageDTO.getSenderId());

        ChatPart chatPart = findChatPartByChatRoomIdAndUserId(messageDTO.getChatRoomId(), messageDTO.getSenderId());

        Message message = ChatConverter.toMessage(messageDTO, chatPart);

        messageRepository.save(message);
    }


    private UserEntity findUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(
                () -> new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatCategoryHandler(ErrorStatus.CHATROOM_NOT_FOUND));
    }

    private ChatPart findChatPartByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        return chatPartRepository.findChatPartByChatRoomIdAndUserEntityId(chatRoomId, userId)
                .orElseThrow(() -> new ChatCategoryHandler(ErrorStatus.CHATPART_NOT_FOUND));
    }

}
