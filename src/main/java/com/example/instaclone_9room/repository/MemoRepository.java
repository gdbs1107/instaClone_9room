package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
