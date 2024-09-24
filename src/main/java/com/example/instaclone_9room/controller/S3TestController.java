package com.example.instaclone_9room.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.example.instaclone_9room.domain.Image;
import com.example.instaclone_9room.repository.ImageRepository;
import com.example.instaclone_9room.service.S3TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/image")
@Tag(name = "이미지 CRUD API", description = "Amazon S3, bucket을 이용한 이미지 CRUD TEST API입니다.")
public class S3TestController {

    @Autowired
    private S3TestService s3Service;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPetImage(
            @RequestParam(value = "dirName") String dirName,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) throws IOException {
        String url = s3Service.upload(multipartFile, dirName);
        log.info("파일 업로드 완료: {}", url);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @GetMapping(path = "/{imageId}")
    public ResponseEntity<byte[]> getPetImage(
            @PathVariable Long imageId
    ) {
        try {
            byte[] fileData = s3Service.download(imageId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename");
            log.info("파일 다운로드 완료: ID {}", imageId);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("파일 다운로드 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{imageId}")
    public ResponseEntity<Void> deletePetImage(
            @PathVariable Long imageId
    ) {
        try {
            s3Service.deleteFile(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("파일 삭제 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
