package com.example.instaclone_9room.service.userService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.domain.userEntity.UserProfileImage;
import com.example.instaclone_9room.repository.ImageRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserProfileImageRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserProfileImageServiceImpl implements UserProfileImageService {


    @PersistenceContext
    private EntityManager entityManager;


    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;



    @Override
    public String upload(MultipartFile multipartFile, String dirName, String username) throws IOException {

        UserEntity findUser = findUser(username);

        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
        String fileName = dirName + "/" + uniqueFileName;
        File uploadFile = convert(multipartFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        // Save metadata to the database
        saveFileMetadata(originalFileName, fileName, multipartFile.getSize(), multipartFile.getContentType(),findUser);

        return uploadImageUrl;
    }



    @Override
    public File convert(MultipartFile file) throws IOException {
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


    @Override
    public String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }


    @Override
    public void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }


    @Override
    public void saveFileMetadata(String originalFileName, String uniqueFileName, long fileSize, String contentType,
                                 UserEntity findUser) {

        String name = findUser.getName();

        UserProfileImage image = UserProfileImage.builder()
                .description(name+"의 프로필 사진입니다")
                .originalFileName(originalFileName)
                .uniqueFileName(uniqueFileName)
                .filePath(uniqueFileName)
                .fileSize(fileSize)
                .uploadDate(LocalDateTime.now())
                .contentType(contentType)
                .status("ACTIVE")
                .userEntity(findUser)
                .build();

        userProfileImageRepository.save(image);
    }


    @Override
    public byte[] download(String username) throws IOException {


        UserEntity findUser = findUser(username);

        UserProfileImage findUserProfileImage = findUser.getUserProfileImages().stream()
                .findFirst()
                .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));


        UserProfileImage image = userProfileImageRepository.findById(findUserProfileImage.getId())
                .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));


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


    @Transactional
    @Override
    public void deleteFile(String username) throws FileNotFoundException {
        try {
            // 유저 찾기
            UserEntity findUser = findUser(username);

            // 첫 번째 프로필 이미지 찾기
            UserProfileImage findUserProfileImage = findUser.getUserProfileImages().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("이미지가 존재하지 않습니다"));

            // 이미지 ID로 조회
            Long userProfileId = findUserProfileImage.getId();
            UserProfileImage image = userProfileImageRepository.findById(userProfileId)
                    .orElseThrow(() -> new RuntimeException("이미지가 존재하지 않습니다"));

            // 파일 경로(S3 키)
            String uniqueFileName = image.getFilePath();
            if (uniqueFileName != null) {
                // AWS S3에서 파일 삭제
                amazonS3.deleteObject(bucket, uniqueFileName);

                findUser.getUserProfileImages().remove(image);
                image.setUserEntity(null);

                log.info("Attempting to delete UserProfileImage with ID: {}", userProfileId);

                // 로컬 DB에서 엔티티 삭제
                userProfileImageRepository.deleteById(userProfileId);

                // 명시적으로 flush 호출
                entityManager.flush();
                // 1차 캐시 클리어
                entityManager.clear();
                log.info("Successfully deleted UserProfileImage with ID: {}", userProfileId);
            } else {
                log.error("파일을 찾을 수 없습니다: ID {}", userProfileId);
            }

        } catch (Exception e) {
            log.error("파일 삭제 중 오류가 발생했습니다: ", e);
            throw new RuntimeException("파일 삭제에 실패했습니다.", e); // 예외 처리 및 롤백 유도
        }
    }



    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }
}
