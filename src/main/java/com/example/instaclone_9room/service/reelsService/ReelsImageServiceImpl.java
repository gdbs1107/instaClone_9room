package com.example.instaclone_9room.service.reelsService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.ReelsCategoryHandler;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsImage;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.repository.reelsRepository.ReelsImageRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
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
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReelsImageServiceImpl implements ReelsImageService {

    @PersistenceContext
    private EntityManager entityManager;


    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final UserRepository userRepository;
    private final ReelsImageRepository reelsImageRepository;
    private final ReelsRepository reelsRepository;



    @Override
    public String upload(MultipartFile multipartFile, String dirName, String username, Long reelsId) throws IOException {

        UserEntity findUser = findUser(username);
        Reels findReels = findReels(reelsId);


        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");
        String fileName = dirName + "/" + uniqueFileName;
        File uploadFile = convert(multipartFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        // Save metadata to the database
        saveFileMetadata(originalFileName, fileName, multipartFile.getSize(), multipartFile.getContentType(),findReels);

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
                                 Reels reels) {

        Long id = reels.getId();

        ReelsImage image = ReelsImage.builder()
                .description(id+"의 릴스입니다")
                .originalFileName(originalFileName)
                .uniqueFileName(uniqueFileName)
                .filePath(uniqueFileName)
                .fileSize(fileSize)
                .uploadDate(LocalDateTime.now())
                .contentType(contentType)
                .status("ACTIVE")
                .reels(reels)
                .build();

        reelsImageRepository.save(image);
    }


    @Override
    public byte[] download(Long reelsId) throws IOException {


        Reels findReels = findReels(reelsId);

        ReelsImage reelsImages = findReels.getReelsImages()
                .stream().findFirst()
                .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));

        ReelsImage image = reelsImageRepository.findById(reelsImages.getId())
                .stream().findFirst()
                .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));;


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
    public void deleteFile(Long reelsId,String username) throws FileNotFoundException {
        try {

            UserEntity findUser = findUser(username);

            if (!username.equals(findUser.getUsername())) {
                throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
            }

            // 첫 번째 프로필 이미지 찾기
            Reels findReels = findReels(reelsId);

            ReelsImage reelsImages = findReels.getReelsImages()
                    .stream().findFirst()
                    .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));

            ReelsImage image = reelsImageRepository.findById(reelsImages.getId())
                    .stream().findFirst()
                    .orElseThrow(()->new RuntimeException("이미지가 존재하지 않습니다"));;

            // 파일 경로(S3 키)
            String uniqueFileName = image.getFilePath();
            if (uniqueFileName != null) {
                // AWS S3에서 파일 삭제
                amazonS3.deleteObject(bucket, uniqueFileName);


                findReels.getReelsImages().remove(image);
                image.serReels(null);


                // 로컬 DB에서 엔티티 삭제
                reelsImageRepository.delete(image);

                // 명시적으로 flush 호출
                entityManager.flush();
                // 1차 캐시 클리어
                entityManager.clear();
            } else {
                log.error("파일을 찾을 수 없습니다: ID {}", findReels.getId());
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

    private Reels findReels(Long id) {
        return reelsRepository.findById(id)
                .orElseThrow(() -> new ReelsCategoryHandler(ErrorStatus.REELS_NOT_FOUND));
    }
}
