package com.example.instaclone_9room.domain;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_entity_id")
    private Long id;

    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ChatPart> chatPartList = new ArrayList<>();

}
