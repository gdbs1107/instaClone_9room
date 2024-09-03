package com.example.instaclone_9room.domain.feedEntity;

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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment extends BaseEntity {
    
    private String content;
    private Integer likesCount;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;
    
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLikes> likes = new ArrayList<>();
    
    //====비즈니스 로직=====//
    public void update(String content) {
    this.content = content;
    }
    
    public void addLike() {
        this.likesCount = this.likesCount == null ? 1 : this.likesCount + 1;
    }
    
    public void removeLike() {
        if (this.likesCount != null && this.likesCount > 0) {
            this.likesCount = this.likesCount - 1;
        }
    }

}
