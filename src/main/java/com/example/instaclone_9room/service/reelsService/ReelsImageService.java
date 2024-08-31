package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.domain.reels.Reels;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface ReelsImageService {
    String upload(MultipartFile multipartFile, String dirName, String username, Long reelsId) throws IOException;

    File convert(MultipartFile file) throws IOException;

    String putS3(File uploadFile, String fileName);

    void removeNewFile(File targetFile);

    void saveFileMetadata(String originalFileName, String uniqueFileName, long fileSize, String contentType,
                          Reels reels);

    byte[] download(Long reelsId) throws IOException;

    @Transactional
    void deleteFile(Long reelsId, String username) throws FileNotFoundException;
}
