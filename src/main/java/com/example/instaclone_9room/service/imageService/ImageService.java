package com.example.instaclone_9room.service.imageService;

import com.example.instaclone_9room.controller.dto.feedDTO.ImageDTO;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface ImageService {
    
    
    List<ImageDTO.ImageResponseDTO> uploadImages(List<MultipartFile> files, String dirName) throws IOException;
    
    void deleteImage(String fileName) throws FileNotFoundException;
    
    ImageDTO.ImageResponseDTO findImageByFileName(String fileName);
    
    List<ImageDTO.ImageResponseDTO> findImagesByFeedId(Long feedId);
}
