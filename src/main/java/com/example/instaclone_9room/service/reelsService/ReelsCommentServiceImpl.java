package com.example.instaclone_9room.service.reelsService;

import com.example.instaclone_9room.apiPayload.exception.UnauthorizedException;
import com.example.instaclone_9room.controller.dto.ReelsCommentDTO;
import com.example.instaclone_9room.converter.ReelsCommentConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.reels.Reels;
import com.example.instaclone_9room.domain.reels.ReelsComment;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsCommentRepository;
import com.example.instaclone_9room.repository.reelsRepository.ReelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReelsCommentServiceImpl implements ReelsCommentService {

    private final ReelsCommentRepository reelsCommentRepository;
    private final ReelsRepository reelsRepository;
    private final UserRepository userRepository;

    @Override
    public void save(ReelsCommentDTO.CommentPostRequestDTO request, String username) {

        UserEntity findUser = findUser(username);
        Reels findReels = findReels(request.getReelsId());

        ReelsComment reelsComment = ReelsCommentConverter.toReelsComment(request, findUser, findReels);
        reelsCommentRepository.save(reelsComment);

    }

    @Override
    public void update(String content,Long id, String username) {

        UserEntity user = findUser(username);

        ReelsComment comment = findComment(id);

        UserEntity commentOwner = comment.getUserEntity();


        if (user.getId().equals(commentOwner.getId())) {
            // 댓글 작성자가 맞다면, 댓글 내용을 업데이트
            comment.updateComment(content);

            // 변경된 내용을 DB에 저장
            reelsCommentRepository.save(comment); // save는 일반적으로 JPA Repository에서 제공하는 메서드
        } else {
            // 작성자가 아니면 예외를 던지거나 오류 처리
            throw new UnauthorizedException("You are not allowed to update this comment.");
        }
    }




    @Override
    public void delete(String username, Long commentId){

        UserEntity user = findUser(username);
        ReelsComment comment = findComment(commentId);
        UserEntity commentOwner = comment.getUserEntity();

        if (user.getId().equals(commentOwner.getId())) {
            reelsCommentRepository.delete(comment);
        } else {
            // 작성자가 아니면 예외를 던지거나 오류 처리
            throw new UnauthorizedException("You are not allowed to update this comment.");
        }

    }













    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Reels findReels(Long id) {
        return reelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reels not found"));
    }

    private ReelsComment findComment(Long id) {
        return reelsCommentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }


}







