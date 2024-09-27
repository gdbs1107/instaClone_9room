package com.example.instaclone_9room.repository.reelsRepository;

import com.example.instaclone_9room.domain.reels.Reels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Long> {

    Page<Reels> findAllByUserEntity_Id(Long userId, Pageable pageable);
}
