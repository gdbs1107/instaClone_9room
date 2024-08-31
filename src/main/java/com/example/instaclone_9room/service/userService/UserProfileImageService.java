package com.example.instaclone_9room.service.userService;

import com.example.instaclone_9room.domain.userEntity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface UserProfileImageService {
    String upload(MultipartFile multipartFile, String dirName, String username) throws IOException;

    File convert(MultipartFile file) throws IOException;

    String putS3(File uploadFile, String fileName);

    void removeNewFile(File targetFile);

    void saveFileMetadata(String originalFileName, String uniqueFileName, long fileSize, String contentType,
                          UserEntity findUser);

    byte[] download(String username) throws IOException;

    void deleteFile(String username) throws FileNotFoundException;
}
