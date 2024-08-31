package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.service.reelsService.ReelsImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/reelsImage")
@Slf4j
@RequiredArgsConstructor
public class ReelsImageController {


    private final ReelsImageService reelsImageService;
    private final UserRepository userRepository;


    @Operation(
            summary = "회원 프로필 사진 등록 API",
            description = "회원의 프로필 사진을 등록 할 수 있는 API입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "사진을 form-data로 MultipartFile 타입으로 요청하시면 저장됩니다."
    )
    @PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "reelsId") Long reelsId,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

        String username = userDetails.getUsername();

        String dirName="reels Image";
        String url = reelsImageService.upload(multipartFile, dirName, username,reelsId);
        log.info("파일 업로드 완료: {}", url);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }




    @Operation(
            summary = "회원 프로필 사진 조회 API",
            description = "회원 프로필 사진 조회 API 입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "토큰 안의 회원정보와 매핑되어 있는 프로필 사진을 가져옵니다"
    )
    @GetMapping(path = "/image")
    public ResponseEntity<byte[]> getPetImage(
            @RequestParam(value = "reelsId") Long reelsId
    ) {

        try {
            byte[] fileData = reelsImageService.download(reelsId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename");
            log.info("파일 다운로드 완료: ID {}", reelsId);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("파일 다운로드 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @Operation(
            summary = "회원 프로필 사진 삭제 API",
            description = "회원 프로필 사진 삭제 API 입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "토큰 안의 회원정보와 매핑되어 있는 프로필 사진을 삭제합니다"
    )
    @DeleteMapping(path = "/image")
    public ResponseEntity<Void> deletePetImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "reelsId") Long reelsId
    ) {
        String username = userDetails.getUsername();
        try {
            reelsImageService.deleteFile(reelsId,username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("파일 삭제 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}