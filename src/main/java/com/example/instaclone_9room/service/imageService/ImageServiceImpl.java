package com.example.instaclone_9room.service.imageService;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.instaclone_9room.controller.dto.feedDTO.ImageDTO;
import com.example.instaclone_9room.converter.feedConverter.ImageConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import com.example.instaclone_9room.repository.postRepository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    
    
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FeedRepository feedRepository;
    private final AmazonS3 s3Client;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    @Override
    public List<ImageDTO.ImageResponseDTO> uploadImages(List<MultipartFile> files, String dirName) {
        List<Image> images = new ArrayList<>();
        
        try {
            images = files.parallelStream()
                    .map(file -> uploadImage(dirName, file))
                    .collect(Collectors.toList());
            
            imageRepository.saveAll(images);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload images", e);
        }
        
        return ImageConverter.toImageDTOList(images);
    }
    
    @Override
    public void deleteImage(String fileName) throws FileNotFoundException {
        Image image = imageRepository.findByFileName(fileName).orElseThrow(
                () -> new FileNotFoundException("Image not found: " + fileName));
        
        // S3에서 이미지 삭제
        try {
            s3Client.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from S3: " + e.getMessage(), e);
        }
        
        // 데이터베이스에서 이미지 레코드 삭제
        imageRepository.delete(image);
    }
    
    @Override
    public ImageDTO.ImageResponseDTO findImageByFileName(String fileName) {
        Image image = imageRepository.findByFileName(fileName).orElseThrow(
                () -> new RuntimeException("Image not found: " + fileName));
        
        return ImageDTO.ImageResponseDTO.builder()
                .fileName(image.getFileName())
                .imagePath(image.getImagePath())
                .build();
    }
    
    
    @Override
    public List<ImageDTO.ImageResponseDTO> findImagesByFeedId(Long feedId) {
        
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new RuntimeException("Feed not found"));
        
        return feed.getImages().stream()
                .map(image -> ImageDTO.ImageResponseDTO.builder()
                        .imagePath(image.getImagePath())
                        .fileName(image.getFileName())
                        .build())
                .collect(Collectors.toList());
    }
    
    private Image uploadImage(String dirName, MultipartFile file) {
        String fileName = dirName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        
        try {
            log.info("Uploading image: {}", fileName);
            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
            log.info("Image uploaded successfully: {}", fileName);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("S3 service error: " + e.getMessage(), e);
        } catch (SdkClientException e) {
            throw new RuntimeException("S3 client error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Image upload error: " + e.getMessage(), e);
        }
        
        String imagePath = s3Client.getUrl(bucket, fileName).toString();
        
        return Image.builder()
                .imagePath(imagePath)
                .fileName(fileName)
                .build();
    }
    
    private Feed findFeed(Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(
                () -> new NotFoundException("Feed not found"));
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));
    }
}
