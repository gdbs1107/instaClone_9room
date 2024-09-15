package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.controller.dto.postDTO.ImageDTO;
import com.example.instaclone_9room.converter.feedconverter.FeedConverter;
import com.example.instaclone_9room.converter.feedconverter.ImageConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import com.example.instaclone_9room.repository.postRepository.ImageRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    
    
    @Override
    public void postFeed(FeedDTO.FeedPostRequestDTO req, String username) throws IOException {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        List<String> fileNames = req.getImages().stream()
                .map(ImageDTO.ImageRequestDTO::getFileName)
                .toList();
        
        List<Image> images = new ArrayList<>();
        
        for (String fileName : fileNames) {
            Image image = imageRepository.findByFileName(fileName).orElseThrow(
                    () -> new RuntimeException("image not found"));
            images.add(image);
        }
        
        Feed newFeed = FeedConverter.toFeed(req, user, images);
        
        imageRepository.saveAll(images);
        feedRepository.save(newFeed);
    }
    
    @Override
    public void updateFeed(Long feedId, FeedDTO.FeedUpdateRequestDTO req, String username) {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        Feed existingFeed = feedRepository.findByIdAndUserEntity(feedId, user).orElseThrow(
                () -> new RuntimeException("feed not found or user not authorized"));
        
        List<Image> images = ImageConverter.toImageList(req.getImages());
        
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
