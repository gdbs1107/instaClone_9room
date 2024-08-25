//package com.example.instaclone_9room.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.*;
//import com.example.instaclone_9room.domain.Image;
//import com.example.instaclone_9room.repository.ImageRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class S3TestService {
//
//    private final AmazonS3 amazonS3;
//    private final ImageRepository imageRepository;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final String DIR_NAME = "profile_image";
//
//    public String upload(String fileName, MultipartFile multipartFile, String extend) throws IOException {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
//        return upload(fileName, uploadFile, extend);
//    }
//
//    private String upload(String fileName, File uploadFile, String extend) {
//        String newFileName = DIR_NAME + "/" + fileName + extend;
//        String uploadImageUrl = putS3(uploadFile, newFileName);
//
//        removeNewFile(uploadFile);
//
//        Image newImage = Image.builder()
//                .fileName(newFileName)
//                .imagePath(uploadImageUrl)
//                .build();
//
//        imageRepository.save(newImage);
//
//        return uploadImageUrl;
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3.putObject(
//                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead)
//        );
//        return amazonS3.getUrl(bucket, fileName).toString();
//    }
//
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        } else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        log.info(file.getOriginalFilename());
//        File convertFile = new File(file.getOriginalFilename());
//        if (convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//        return Optional.empty();
//    }
//
//    public ResponseEntity<byte[]> download(String fileName) throws IOException {
//        S3Object awsS3Object = amazonS3.getObject(new GetObjectRequest(bucket, DIR_NAME + "/" + fileName));
//        S3ObjectInputStream s3is = awsS3Object.getObjectContent();
//        byte[] bytes = s3is.readAllBytes();
//
//        String downloadedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//        httpHeaders.setContentLength(bytes.length);
//        httpHeaders.setContentDispositionFormData("attachment", downloadedFileName);
//        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
//    }
//
//    public void deleteImage(Long imageId) {
//        // 이미지 정보 조회
//        Image image = imageRepository.findById(imageId)
//                .orElseThrow(() -> new IllegalArgumentException("이미지 ID가 존재하지 않습니다."));
//
//        // S3에서 파일 삭제
//        String fileName = image.getFileName();
//        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
//
//        // 데이터베이스에서 이미지 삭제
//        imageRepository.delete(image);
//    }
//}
