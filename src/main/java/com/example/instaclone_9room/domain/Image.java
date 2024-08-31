package com.example.instaclone_9room.domain;

import com.example.instaclone_9room.domain.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

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

    // Getters and Setters
    // Add constructors, equals(), and hashCode() methods if necessary
}
