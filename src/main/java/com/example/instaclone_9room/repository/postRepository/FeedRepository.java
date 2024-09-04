package com.example.instaclone_9room.repository.postRepository;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    
    @Query("select f from Feed f left join f.userEntity u")
    Optional<Feed> findByIdAndUserEntity(Long communityId, UserEntity userEntity);
    
    @Query("SELECT f FROM Feed f WHERE f.userEntity.username = :username")
    List<Feed> findAllFeedByUsername(String username);
}
