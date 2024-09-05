package com.example.instaclone_9room.domain;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Memo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String content;

    @OneToOne
    @JoinColumn(name = "user_entity_id", unique = true)
    private UserEntity userEntity;
}
