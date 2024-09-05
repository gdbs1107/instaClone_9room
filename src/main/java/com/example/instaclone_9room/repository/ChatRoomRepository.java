package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.DM.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


}
