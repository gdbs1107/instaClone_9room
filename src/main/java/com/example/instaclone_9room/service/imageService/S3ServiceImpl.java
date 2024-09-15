package com.example.instaclone_9room.service.imageService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.instaclone_9room.repository.postRepository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class S3ServiceImpl implements S3Service{
    
    private final AmazonS3 amazonS3;
    private final String bucket;
    
    @Autowired
    public S3ServiceImpl(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket, ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }
    
    @Override
    public String upload(MultipartFile file, String dirName) throws IOException{
        String fileName = dirName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        
        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        
        return amazonS3.getUrl(bucket, fileName).toString();
    }
    
    @Override
    public void delete(String imagePath, String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }
    
}