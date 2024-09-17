package com.example.instaclone_9room.repository.DmRepository;

import com.example.instaclone_9room.domain.DM.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
