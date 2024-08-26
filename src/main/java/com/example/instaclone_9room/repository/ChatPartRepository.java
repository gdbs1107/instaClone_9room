package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.ChatPart;
import com.example.instaclone_9room.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatPartRepository extends JpaRepository<ChatPart,Long> {

    List<ChatPart> findByChatRoom(ChatRoom chatRoom);

}
