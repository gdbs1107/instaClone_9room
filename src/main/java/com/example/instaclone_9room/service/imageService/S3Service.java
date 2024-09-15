package com.example.instaclone_9room.service.imageService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface S3Service {
    
    String upload(MultipartFile file, String dirName) throws IOException;
    
    void delete(String imagePath, String dirName);
}
