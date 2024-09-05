package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.DM.ChatPart;
import com.example.instaclone_9room.domain.DM.ChatRoom;
import com.example.instaclone_9room.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatPartRepository extends JpaRepository<ChatPart,Long> {

}
