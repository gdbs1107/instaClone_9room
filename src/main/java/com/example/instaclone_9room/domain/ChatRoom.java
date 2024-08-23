package com.example.instaclone_9room.domain;

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
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.ALL)
    private List<ChatPart> chatPartList = new ArrayList<>();
}
