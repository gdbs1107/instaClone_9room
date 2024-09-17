package com.example.instaclone_9room.repository.DmRepository;

import com.example.instaclone_9room.domain.DM.ChatPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatPartRepository extends JpaRepository<ChatPart, Long> {

    List<ChatPart> findAllByChatRoomId(Long chatRoomId);

    Optional<ChatPart> findByChatRoomId(Long chatRoomId);

    Optional<ChatPart> findByUserEntityId(Long userEntityId);

    List<ChatPart> findAllByUserEntityId(Long userId);

    Optional<ChatPart> findChatPartByChatRoomIdAndUserEntityId(Long chatRoomId, Long senderId);
}
