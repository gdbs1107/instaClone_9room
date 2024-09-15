package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.feedDTO.ImageDTO;
import com.example.instaclone_9room.service.imageService.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed/{feedId}/image")
public class FeedImageController {
    
    private final ImageService imageService;
    
    
    //이미지 파일들 s3에 저장 후 테이블 추가
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<ImageDTO.ImageResponseDTO>> addImages(@RequestPart List<MultipartFile> files) {
        
        String dirName = "Feed Image";
        List<ImageDTO.ImageResponseDTO> imageResponseDTOS = imageService.uploadImages(files, dirName);
        
        return ApiResponse.onSuccess(imageResponseDTOS);
    }
    
    // 이미지 삭제
    @DeleteMapping("/")
    public ApiResponse<String> deleteImage(@RequestParam String fileName) throws FileNotFoundException {
        
        imageService.deleteImage(fileName);
        return ApiResponse.onSuccess("Image deleted successfully");
    }
    
    // 이미지 조회
    @GetMapping("/")
    public ApiResponse<ImageDTO.ImageResponseDTO> getImageByFileName(
            @RequestParam String fileName) {
        
        ImageDTO.ImageResponseDTO imageResponseDTO = imageService.findImageByFileName(fileName);
        
        return ApiResponse.onSuccess(imageResponseDTO);
    }
    
    @GetMapping("/all")
    public ApiResponse<List<ImageDTO.ImageResponseDTO>> getImagesByFeedId(
            @PathVariable Long feedId) {
        
        List<ImageDTO.ImageResponseDTO> imageResponseDTOs = imageService.findImagesByFeedId(feedId);
        
        return ApiResponse.onSuccess(imageResponseDTOs);
    }
    
}
