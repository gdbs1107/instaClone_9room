package com.example.instaclone_9room.repository.storyRepository;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedLikes;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import com.example.instaclone_9room.domain.storyEntitiy.StoryLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryLikesRepository extends JpaRepository<StoryLikes, Long> {
    
    Optional<StoryLikes> findByUserEntityAndStory(UserEntity userEntity, Story story);
    
}
