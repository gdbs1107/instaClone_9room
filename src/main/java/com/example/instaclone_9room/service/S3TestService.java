package com.example.instaclone_9room.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.instaclone_9room.domain.Image;
import com.example.instaclone_9room.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class S3TestService {

    private final AmazonS3 amazonS3;
    private final String bucket;
    private final ImageRepository imageRepository;

    @Autowired
    public S3TestService(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket, ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.imageRepository = imageRepository;
    }

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
        String fileName = dirName + "/" + uniqueFileName;
        File uploadFile = convert(multipartFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        // Save metadata to the database
        saveFileMetadata(originalFileName, fileName, multipartFile.getSize(), multipartFile.getContentType());

        return uploadImageUrl;
    }

    private File convert(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

        File convertFile = new File(uniqueFileName);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("파일 변환 중 오류 발생: {}", e.getMessage());
                throw e;
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private void saveFileMetadata(String originalFileName, String uniqueFileName, long fileSize, String contentType) {

        Image image = Image.builder()
                .originalFileName(originalFileName)
                .uniqueFileName(uniqueFileName)
                .filePath(uniqueFileName)
                .fileSize(fileSize)
                .uploadDate(LocalDateTime.now())
                .contentType(contentType)
                .status("ACTIVE")
                .build();

        imageRepository.save(image);
    }

    public byte[] download(Long imageId) throws IOException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + imageId));

        String uniqueFileName = image.getFilePath();  // Use full path as S3 key
        log.info("Downloading file from S3 with key: {}", uniqueFileName);

        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, uniqueFileName));
            try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
                return inputStream.readAllBytes();
            }
        } catch (AmazonS3Exception e) {
            log.error("S3에서 파일 다운로드 오류: {}", e.getMessage());
            throw e;
        }
    }

    public void deleteFile(Long imageId) throws FileNotFoundException {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new FileNotFoundException("File not found with ID: " + imageId));

        String uniqueFileName = image.getFilePath();  // Use full path as S3 key
        if (uniqueFileName != null) {
            amazonS3.deleteObject(bucket, uniqueFileName);
            imageRepository.deleteById(imageId);
        } else {
            log.error("파일을 찾을 수 없습니다: ID {}", imageId);
        }
    }
}
