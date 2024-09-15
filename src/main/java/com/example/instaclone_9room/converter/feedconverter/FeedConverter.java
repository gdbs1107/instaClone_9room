package com.example.instaclone_9room.converter.feedconverter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedCommentDTO;
import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FeedConverter {
    
    public static Feed toFeed(FeedDTO.FeedPostRequestDTO req, UserEntity user, List<Image> images) {
        
        Feed feed = Feed.builder()
                .content(req.getContent())
                .location(req.getLocation())
                .images(images)
                .likesCount(0)
                .commentCount(0)
                .userEntity(user)
                .build();
        
        feed.setImages(images);
        
        return feed;
    }
    
    public static FeedDTO.FeedResponseDTO toFeedResponseDTO(Feed feed) {
        List<FeedCommentDTO.CommentResponseDTO> feedCommentDTOs = feed.getFeedComments().stream()
                .map(comment -> FeedCommentDTO.CommentResponseDTO.builder()
                        .content(comment.getContent())
                        .name(comment.getUserEntity().getUsername())
                        .build())
                .collect(Collectors.toList());
        
        return FeedDTO.FeedResponseDTO.builder()
                .comments(feedCommentDTOs)
                .commentsCount(feed.getCommentCount())
                .content(feed.getContent())
                .images(ImageConverter.toImageDTOList(feed.getImages()))
                .likesCount(feed.getLikesCount())
                .location(feed.getLocation())
                .build();
    }
}
