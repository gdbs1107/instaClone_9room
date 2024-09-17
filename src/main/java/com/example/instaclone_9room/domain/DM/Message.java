package com.example.instaclone_9room.domain.DM;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;


import com.example.instaclone_9room.domain.enumPackage.ReadStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    // 메세지 읽음 확인
    private ReadStatus isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_part_id")
    ChatPart chatPart;


}
