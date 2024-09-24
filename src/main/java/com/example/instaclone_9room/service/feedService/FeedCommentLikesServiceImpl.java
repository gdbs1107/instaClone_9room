package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.FeedComment;
import com.example.instaclone_9room.domain.feedEntity.FeedCommentLikes;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedCommentLikesRepository;
import com.example.instaclone_9room.repository.postRepository.FeedCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedCommentLikesServiceImpl implements FeedCommentLikesService {
    
    private final FeedCommentLikesRepository feedCommentLikesRepository;
    private final UserRepository userRepository;
    private final FeedCommentRepository feedCommentRepository;
    
    @Override
    public void toggleFeedCommentLike(Long commentId, String username) {
        UserEntity findUser = findUser(username);
        
        FeedComment comment = feedCommentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("comment not found"));
        
        
        FeedCommentLikes feedCommentLikes = feedCommentLikesRepository.findByUserEntityAndFeedComment(findUser, comment)
                .orElse(null);
        
        if(feedCommentLikes!=null){
            
            comment.removeLike();
            feedCommentLikesRepository.delete(feedCommentLikes);
            
        }else {
            
            FeedCommentLikes newFeedCommentLikes = FeedCommentLikes.builder()
                    .feedComment(comment)
                    .userEntity(findUser)
                    .build();
            
            comment.addLike();
            
            feedCommentLikesRepository.save(newFeedCommentLikes);
        }
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
}
