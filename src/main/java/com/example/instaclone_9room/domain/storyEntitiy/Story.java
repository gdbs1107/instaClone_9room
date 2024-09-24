package com.example.instaclone_9room.domain.storyEntitiy;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Story extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private Long id;
    
    private String fileName;
    private String imagePath;
    private Boolean viewable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;
    
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryLikes> likes = new ArrayList<>();
    private Integer likesCount;
    
    public void editViewable() {
        LocalDateTime now = LocalDateTime.now();
        this.viewable = !now.isAfter(getCreatedAt().plusHours(24));
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
