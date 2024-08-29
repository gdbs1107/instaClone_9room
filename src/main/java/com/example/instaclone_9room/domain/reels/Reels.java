package com.example.instaclone_9room.domain.reels;

import com.example.instaclone_9room.domain.UserEntity;
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
@NoArgsConstructor
@AllArgsConstructor
public class Reels extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoPath;
    private String videoName;

    private String audioPath;
    private String audioName;

    @Column(nullable = false, length = 100)
    private String content;

    private Integer commentCount;
    private Integer likesCount;
    private Integer pinnedCount;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "reels",cascade = CascadeType.ALL)
    private List<ReelsComment> reelsComments =new ArrayList<>();



    public void setUserEntity(UserEntity userEntity) {
        if(userEntity != null) {
            this.userEntity = userEntity;
        }else {
            return;
        }
    }

    public void update(String content, String audioPath, String audioName) {
        this.content = content;
        this.audioPath = audioPath;
        this.audioName = audioName;
    }




    public void addReelsCommentCount(){
        this.commentCount++;
    }

    public void minusReelsCommentCount(){
        this.commentCount--;
    }




    public void addReelsLikeCount(){
        this.likesCount++;
    }
    public void minusReelsLikeCount(){
        this.likesCount--;
    }


    public void addReelsPinnedCount(){
        this.pinnedCount++;
    }
    public void minusReelsPinnedCount(){
        this.pinnedCount--;
    }


}
