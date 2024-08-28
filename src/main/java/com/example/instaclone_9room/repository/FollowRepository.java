package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
