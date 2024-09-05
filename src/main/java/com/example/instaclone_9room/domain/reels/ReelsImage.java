package com.example.instaclone_9room.domain.reels;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReelsImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String uniqueFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @Column(nullable = false)
    private String contentType;

    @Column
    private String description;

    @Column
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reels_id")
    private Reels reels;






    public void serReels(Reels reels) {
        if (this.reels == null) {
            return;
        }else {
            this.reels = reels;
        }
    }
}
