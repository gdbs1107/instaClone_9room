package com.example.instaclone_9room.converter.feedconverter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
