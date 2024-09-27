package com.example.instaclone_9room.domain.DM;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatPart> chatPartList = new ArrayList<>();

    public List<Long> getParticipantIds() {
        return chatPartList.stream()
                .map(chatPart -> chatPart.getUserEntity().getId())
                .collect(Collectors.toList());
    }

    public List<Long> getChatRoomIdByUser(UserEntity user) {
        return chatPartList.stream()
                .filter(chatPart -> chatPart.getUserEntity().equals(user))
                .map(chatPart -> chatPart.getChatRoom().getId())
                .collect(Collectors.toList());
    }
}

