package com.example.instaclone_9room.service.storyService;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.converter.storyConverter.StoryConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.storyRepository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {
    
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    @Override
    public void postStory(MultipartFile file, String username, String dirName) {
        
        UserEntity findUser = findUser(username);
        Story story = uploadStoryImage(dirName, file);
        
        Story saveStory = StoryConverter.toStory(story, findUser);
        
        storyRepository.save(saveStory);
    }
    
    @Override
    public void deleteStory(Long storyId, String username) {
        
        UserEntity findUser = findUser(username);
        
        Story story = findStory(storyId);
        
        userValidate(story, findUser);
        
        try {
            amazonS3.deleteObject(bucket, story.getFileName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from S3: " + e.getMessage(), e);
        }
        
        storyRepository.delete(story);
    }
    
    @Override
    public StoryDTO.StoryResponseDTO searchStory(Long storyId) {
        Story story = findStory(storyId);
        story.editViewable();
        
        return StoryConverter.toStoryResponseDTO(story);
    }
    
    //회원 아이디 조회시 그 회원의 스토리중 올린지 24시간 전에 스토리들만 반환
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
    
    private Story findStory(Long storyId) {
        return storyRepository.findById(storyId).orElseThrow(
                () -> new RuntimeException("Story not found"));
    }
    
    private static void userValidate(Story story, UserEntity user) {
        if (!story.getUserEntity().equals(user)) {
            throw new RuntimeException("no permission");
        }
    }
    
    private Story uploadStoryImage(String dirName, MultipartFile file) {
        String fileName = dirName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
        } catch (AmazonServiceException e) {
            throw new RuntimeException("S3 service error: " + e.getMessage(), e);
        } catch (SdkClientException e) {
            throw new RuntimeException("S3 client error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Image upload error: " + e.getMessage(), e);
        }
        
        String imagePath = amazonS3.getUrl(bucket, fileName).toString();
        
        return Story.builder()
                .imagePath(imagePath)
                .fileName(fileName)
                .build();
    }
}
