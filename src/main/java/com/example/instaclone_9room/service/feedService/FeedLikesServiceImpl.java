package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedLikes;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedLikesRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikesServiceImpl implements FeedLikesService {
    
    private final FeedLikesRepository feedLikesRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    
    @Override
    public void toggleFeedLike(Long feedId, String username) {
        UserEntity findUser = findUser(username);
        
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new RuntimeException("feed not found"));
        
        
        FeedLikes feedLikes = feedLikesRepository.findByUserEntityAndFeed(findUser, feed)
                .orElse(null);
        
        if(feedLikes!=null){
            
            feed.removeLike();
            feedLikesRepository.delete(feedLikes);
            
        }else {
            
            FeedLikes newFeedLikes = FeedLikes.builder()
                    .feed(feed)
                    .userEntity(findUser)
                    .build();
            
            feed.addLike();
            
            feedLikesRepository.save(newFeedLikes);
        }
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
}
