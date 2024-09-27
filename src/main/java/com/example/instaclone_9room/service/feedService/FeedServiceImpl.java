package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import com.example.instaclone_9room.controller.dto.feedDTO.ImageDTO;
import com.example.instaclone_9room.converter.feedConverter.FeedConverter;
import com.example.instaclone_9room.converter.feedConverter.ImageConverter;
import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import com.example.instaclone_9room.repository.postRepository.FeedImageRepository;
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
    private final FeedImageRepository feedImageRepository;
    
    
    @Override
    public void postFeed(FeedDTO.FeedPostRequestDTO req, String username) throws IOException {
        
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        List<String> fileNames = req.getImages().stream()
                .map(ImageDTO.ImageRequestDTO::getFileName)
                .toList();
        
        List<Image> images = new ArrayList<>();
        
        for (String fileName : fileNames) {
            Image image = feedImageRepository.findByFileName(fileName).orElseThrow(
                    () -> new RuntimeException("image not found"));
            images.add(image);
        }
        
        Feed newFeed = FeedConverter.toFeed(req, user, images);
        
        feedImageRepository.saveAll(images);
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
        
        feedImageRepository.saveAll(existingFeed.getImages());
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
    public FeedDTO.FeedResponseDTO searchFeed(Long feedId) {
        Feed existingFeed = feedRepository.findById(feedId).orElseThrow(
                () -> new RuntimeException("feed not found"));
        
        return FeedConverter.toFeedResponseDTO(existingFeed);
    }
    
    @Override
    public List<FeedDTO.FeedSmallResponseDTO> searchFeedByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));
        
        List<Feed> feedList = feedRepository.findAllFeedByUsername(username);
        
        return FeedConverter.toFeedSmallResponseDTOList(feedList);
    }
    
    private static void userValidate(Feed existingFeed, UserEntity user) {
        if (!existingFeed.getUserEntity().equals(user)) {
            throw new RuntimeException("no permission");
        }
    }
}
