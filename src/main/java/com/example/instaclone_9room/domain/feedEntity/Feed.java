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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id; //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    private Integer commentCount;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLikes> likes = new ArrayList<>();
    private Integer likesCount;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    private String location;
    
    //======비즈니스 로직======//
    public void update(String content, String location, List<Image> images) {
        this.content = content;
        this.location = location;
        this.images = images;
    }
    
    public void addLike() {
        this.likesCount = this.likesCount == null ? 1 : this.likesCount + 1;
    }
    
    public void removeLike() {
        if (this.likesCount != null && this.likesCount > 0) {
            this.likesCount = this.likesCount - 1;
        }
    }
    
    public void updateCommentCount() {
        this.commentCount = this.comments.size();
    }

}
