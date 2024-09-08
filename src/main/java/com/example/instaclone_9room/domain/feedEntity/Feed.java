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
    
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLikes> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    
    @Column(nullable = false)
    private String content;
    
    private Integer commentCount;
    private Integer likesCount;
    private String location;
//    private Integer pinnedCount;
    
    //======비즈니스 로직======//
    public void update(String content, String location) {
        this.content = content;
        this.location = location;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
        images.forEach(image -> image.setFeed(this));
    }
    
    public void addLike() {
        this.likesCount = this.likesCount == null ? 1 : this.likesCount + 1;
    }
    
    public void removeLike() {
        if (this.likesCount != null && this.likesCount > 0) {
            this.likesCount = this.likesCount - 1;
        }
    }
    
    public void updateImages(List<Image> images) {
        this.images = images;
    }
    
    public void updateCommentCount() {
        this.commentCount = this.comments.size();
    }
    
//    public void addPinCount() {
//        this.pinnedCount = this.pinnedCount + 1;
//    }

//    public void removePinCount() {
//        this.pinnedCount = this.pinnedCount -1;
//    }

}
