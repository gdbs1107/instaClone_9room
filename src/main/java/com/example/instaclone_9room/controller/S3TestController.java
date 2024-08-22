package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.service.S3TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class S3TestController {

    @Autowired
    private S3TestService s3Service;

    @PostMapping(path = "/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPetImage(
            @RequestPart(value = "fileName") String fileName,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile
    ) throws IOException {
        String extend = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String url = s3Service.upload(fileName,multipartFile,extend);
        log.info(url);
        return new ResponseEntity(url,null, HttpStatus.OK);
    }

    @GetMapping(path = "/teams/{fileName}")
    public ResponseEntity<byte[]> getPetImage(
            @PathVariable String fileName
    ) throws IOException {
        return s3Service.download(fileName);
    }
}
