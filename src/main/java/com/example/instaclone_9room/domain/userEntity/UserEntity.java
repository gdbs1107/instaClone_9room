package com.example.instaclone_9room.domain.userEntity;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;
import com.example.instaclone_9room.domain.enumPackage.Gender;
import com.example.instaclone_9room.domain.follow.Follow;
import com.example.instaclone_9room.domain.follow.Follower;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsComment;
import com.example.instaclone_9room.domain.reels.ReelsLikes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    //해싱돼서 제한을 걸 수 없음
    private String password;

    private String role;

    @Column(nullable = false, length = 20)
    private String name;

    private Boolean onPrivate=false;

    @Column(length = 30)
    @Nullable
    private String introduction;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthday;

    @Column(length = 30)
    private String link;

    @Nullable
    private Integer followCount;
    @Nullable
    private Integer followerCount;



    private String email;




    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<Reels> reels = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ReelsComment> reelsComments = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ReelsLikes> reelsLikes = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<UserProfileImage> userProfileImages = new ArrayList<>();






    //--------연관관계 메서드---------------


    public void setInfo(String name,Gender gender,LocalDate birthday,String link,String introduction,Boolean onPrivate){
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.link = link;
        this.introduction = introduction;
        this.onPrivate=onPrivate;
    }



    public void addReels(Reels reels) {
        reels.setUserEntity(this);
        this.reels.add(reels);
    }

    public void addFollowCount(){
        this.followCount++;
    }
    public void minusFollowCount(){
        this.followCount--;
    }



    public void addFollowerCount(){
        this.followerCount++;
    }
    public void minusFollowerCount(){
        this.followerCount--;
    }


    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public void setName(String name) {
        if (name != null) {
            this.name=name;
        }
    }




}
