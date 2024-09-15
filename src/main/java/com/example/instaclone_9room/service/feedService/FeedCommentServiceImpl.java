package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedCommentDTO;
import com.example.instaclone_9room.converter.feedconverter.FeedCommentConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.FeedComment;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedCommentRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService {
    
    private final FeedCommentRepository feedCommentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    
    @Override
    public void save(FeedCommentDTO.CommentPostRequestDTO request, String username) {
        
        UserEntity findUser = findUser(username);
        Feed findFeed = findFeed(request.getFeedId());
        
        FeedComment feedComment = FeedCommentConverter.toFeedComment(request, findUser, findFeed);
        feedCommentRepository.save(feedComment);
        findFeed.updateCommentCount();
    }
    
    @Override
    public void update(String content, Long id, String username) {
        UserEntity user = findUser(username);
        
        FeedComment comment = findComment(id);
        
        UserEntity commentOwner = comment.getUserEntity();
        
        
        if (user.getId().equals(commentOwner.getId())) {
            comment.update(content);
            
            feedCommentRepository.save(comment);
        } else {
            throw new RuntimeException("User is not owner of this comment");
        }
    }
    
    @Override
    public void delete(String username, Long commentId) {
        UserEntity user = findUser(username);
        FeedComment comment = findComment(commentId);
        
        Feed feed = comment.getFeed();
        
        
        UserEntity commentOwner = comment.getUserEntity();
        
        if (user.getId().equals(commentOwner.getId())) {
            log.info("Deleting comment {}", commentId);
            feed.getFeedComments().remove(comment);
            feedCommentRepository.delete(comment);
            log.info("Deleted comment {}", commentId);
            feed.updateCommentCount();
            
        } else {
            throw new RuntimeException("User is not owner of this comment");
        }
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
    
    private Feed findFeed(Long id) {
        return feedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feed not found"));
    }
    
    private FeedComment findComment(Long id) {
        return feedCommentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeedComment not found"));
    }
}
