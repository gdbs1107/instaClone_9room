package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.follow.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
