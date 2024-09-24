package com.example.instaclone_9room.service.storyService;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedLikes;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import com.example.instaclone_9room.domain.storyEntitiy.StoryLikes;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.repository.storyRepository.StoryLikesRepository;
import com.example.instaclone_9room.repository.storyRepository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryLikesServiceImpl implements StoryLikesService {
    
    private final StoryLikesRepository storyLikesRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    
    @Override
    public void toggleStoryLike(Long storyId, String username) {
        UserEntity findUser = findUser(username);
        
        Story story = storyRepository.findById(storyId).orElseThrow(
                () -> new RuntimeException("story not found"));
        
        
        StoryLikes storyLikes = storyLikesRepository.findByUserEntityAndStory(findUser, story)
                .orElse(null);
        
        if(storyLikes!=null){
            
            story.removeLike();
            storyLikesRepository.delete(storyLikes);
            
        }else {
            
            StoryLikes newStoryLikes = StoryLikes.builder()
                    .story(story)
                    .userEntity(findUser)
                    .build();
            
            story.addLike();
            
            storyLikesRepository.save(newStoryLikes);
        }
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
}
