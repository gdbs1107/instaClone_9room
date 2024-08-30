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
@Tag(name = "이미지 CRUD API", description = "Amazon S3, bucket을 이용한 이미지 CRUD TEST API입니다.")
public class S3TestController {

    @Autowired
    private S3TestService s3Service;





    @PostMapping(path = "/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPetImage(
            @RequestPart(value = "fileName") String fileName,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile
    ) throws IOException {
        String extend = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String url = s3Service.upload(fileName, multipartFile, extend);
        log.info(url);
        return new ResponseEntity<>(url, null, HttpStatus.OK);
    }





    @GetMapping(path = "/teams/{fileName}")
    public ResponseEntity<byte[]> getPetImage(
            @PathVariable String fileName
    ) throws IOException {
        return s3Service.download(fileName);
    }





    @DeleteMapping(path = "/teams/{imageId}")
    public ResponseEntity<Void> deletePetImage(
            @PathVariable Long imageId
    ) {
        try {
            s3Service.deleteImage(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("이미지 삭제 실패: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
