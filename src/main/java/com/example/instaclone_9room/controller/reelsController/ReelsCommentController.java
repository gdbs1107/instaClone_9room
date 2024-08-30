package com.example.instaclone_9room.controller.reelsController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import com.example.instaclone_9room.service.reelsService.ReelsCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reelsComment")
@Tag(name = "릴스 댓글 API", description = "릴스 댓글 API입니다.")
public class ReelsCommentController {

    private final ReelsCommentService reelsCommentService;


    @Operation(
            summary = "댓글 등록 API",
            description = "댓글 등록 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @PostMapping("/")
    public ApiResponse<String> save(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestBody @Valid ReelsCommentDTO.CommentPostRequestDTO requestDTO) {

        reelsCommentService.save(requestDTO,userDetails.getUsername());
        return ApiResponse.onSuccess("saved comment successfully");
    }



    @Operation(
            summary = "댓글 수정 API",
            description = "댓글 수정 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @PutMapping("/{id}")
    public ApiResponse<String> update(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id,
                                      @RequestBody @Valid String content){

        reelsCommentService.update(content,id,userDetails.getUsername());
        return ApiResponse.onSuccess("updated comment successfully");
    }




    @Operation(
            summary = "댓글 삭제 API",
            description = "댓글 삭제 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable Long id){
        reelsCommentService.delete(userDetails.getUsername(),id);
        return ApiResponse.onSuccess("deleted comment successfully");
    }
}
