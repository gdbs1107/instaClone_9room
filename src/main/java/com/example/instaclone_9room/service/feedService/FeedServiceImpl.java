package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.converter.FeedConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    
    
    @Override
    public void postFeed(FeedDTO.FeedPostRequestDTO feedPostRequestDTO, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed newFeed = FeedConverter.toFeed(feedPostRequestDTO, user);
        feedRepository.save(newFeed);
    }
    
    @Override
    public void updateFeed(Long feedId, FeedDTO.FeedUpdateRequestDTO feedUpdateRequestDTO, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed existingFeed = feedRepository.findByIdAndUserEntity(feedId, user).orElseThrow(
                () -> new RuntimeException("feed not found or user not authorized"));
        
        existingFeed.update(feedUpdateRequestDTO.getContent(),
                            feedUpdateRequestDTO.getLocation(),
                            feedUpdateRequestDTO.getImages());
        
        feedRepository.save(existingFeed);
    
    }
    
    @Override
    public void deleteFeed(Long feedId, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed existingFeed = feedRepository.findById(feedId).orElseThrow(
                () -> new RuntimeException("feed not found"));
        
        userValidate(existingFeed, user);
        
        feedRepository.delete(existingFeed);
    }
    
    @Override
    public void searchFeed(Long feedId) {
    
    }
    
    private static void userValidate(Feed existingFeed, UserEntity user) {
        if (!existingFeed.getUserEntity().equals(user)) {
            throw new RuntimeException("no permission");
        }
    }
}
