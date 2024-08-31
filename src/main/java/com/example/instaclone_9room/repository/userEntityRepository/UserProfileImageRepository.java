package com.example.instaclone_9room.repository.userEntityRepository;


import com.example.instaclone_9room.domain.userEntity.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
    void deleteByUniqueFileName(String uniqueFileName);
}
