package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.converter.FeedConverter;
import com.example.instaclone_9room.converter.ImageConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import com.example.instaclone_9room.repository.postRepository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    
    
    @Override
    public void postFeed(FeedDTO.FeedPostRequestDTO req, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed newFeed = FeedConverter.toFeed(req, user);
        
        imageRepository.saveAll(newFeed.getImages());
        feedRepository.save(newFeed);
    }
    
    @Override
    public void updateFeed(Long feedId, FeedDTO.FeedUpdateRequestDTO req, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed existingFeed = feedRepository.findByIdAndUserEntity(feedId, user).orElseThrow(
                () -> new RuntimeException("feed not found or user not authorized"));
        
        List<Image> images = ImageConverter.toImageList(req.getImageDTOS());
        
        existingFeed.getImages().forEach(image -> image.setFeed(null));
        existingFeed.getImages().clear();
        
        images.forEach(image -> image.setFeed(existingFeed));
        existingFeed.getImages().addAll(images);
        
        existingFeed.update(req.getContent(), req.getLocation()); //본문 지역 제거
        
        imageRepository.saveAll(existingFeed.getImages());
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
