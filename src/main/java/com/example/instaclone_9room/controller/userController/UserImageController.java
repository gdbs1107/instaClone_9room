package com.example.instaclone_9room.controller.userController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.service.userService.UserCommandService;
import com.example.instaclone_9room.service.userService.UserProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Tag(name = "회원 프로필 이미지 관련 API", description = "회원 프로필 이미지 API 입니다")
public class UserImageController {



    private final UserProfileImageService userProfileImageService;



    @Operation(
            summary = "회원 프로필 사진 등록 API",
            description = "회원의 프로필 사진을 등록 할 수 있는 API입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "사진을 form-data로 MultipartFile 타입으로 요청하시면 저장됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6001", description = "파일 변환에 실패하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6002", description = "이미지를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6003", description = "Amazon S3에서 파일을 불러올 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

        String username = userDetails.getUsername();

        String dirName="User Profile Image";
        String url = userProfileImageService.upload(multipartFile, dirName, username);
        log.info("파일 업로드 완료: {}", url);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }




    @Operation(
            summary = "회원 프로필 사진 조회 API",
            description = "회원 프로필 사진 조회 API 입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "토큰 안의 회원정보와 매핑되어 있는 프로필 사진을 가져옵니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6001", description = "파일 변환에 실패하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6002", description = "이미지를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6003", description = "Amazon S3에서 파일을 불러올 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping(path = "/image")
    public ResponseEntity<byte[]> getPetImage(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();
        try {
            byte[] fileData = userProfileImageService.download(username);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename");
            log.info("파일 다운로드 완료: ID {}", username);
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6001", description = "파일 변환에 실패하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6002", description = "이미지를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6003", description = "Amazon S3에서 파일을 불러올 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6004", description = "이미지 삭제에 실패하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @DeleteMapping(path = "/image")
    public ResponseEntity<Void> deletePetImage(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        try {
            userProfileImageService.deleteFile(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("파일 삭제 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @Operation(
            summary = "회원 프로필 사진 업데이트 API",
            description = "회원의 프로필 사진을 업데이트 할 수 있는 API입니다.<br>" +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "사진을 form-data로 MultipartFile 타입으로 요청하시면 기존 사진이 삭제되고 새로운 사진이 저장됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6001", description = "파일 변환에 실패하였습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6002", description = "이미지를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE6003", description = "Amazon S3에서 파일을 불러올 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping(path = "/image/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfileImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        String username = userDetails.getUsername();
        String dirName = "User Profile Image";
        try {
            String url = userProfileImageService.updateProfileImage(multipartFile, dirName, username);
            log.info("프로필 이미지 업데이트 완료: {}", url);
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (Exception e) {
            log.error("프로필 이미지 업데이트 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
