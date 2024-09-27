package com.example.instaclone_9room.repository.userEntityRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
    UserEntity getByUsername(String username);


}
