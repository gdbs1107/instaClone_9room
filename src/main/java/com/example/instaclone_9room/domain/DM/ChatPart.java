package com.example.instaclone_9room.domain.DM;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.baseEntity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class ChatPart extends BaseEntity {


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "chatPart", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();
}
