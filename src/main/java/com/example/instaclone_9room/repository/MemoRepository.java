package com.example.instaclone_9room.repository;

import com.example.instaclone_9room.domain.Memo;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    Page<Memo> findAllByUserEntityIn(List<UserEntity> users, Pageable pageable);

    Optional<Memo> findByUserEntity(UserEntity userEntity);

    List<Memo> findByUpdatedAtBefore(LocalDateTime dateTime);
}
