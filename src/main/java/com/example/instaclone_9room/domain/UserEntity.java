package com.example.instaclone_9room.domain;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String name;
    private Boolean onPrivate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthday;
    private String link;


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ChatPart> chatPartList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<Reels> reels=new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ReelsComment> reelsComments=new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ReelsLikes> reelsLikes=new ArrayList<>();



}
