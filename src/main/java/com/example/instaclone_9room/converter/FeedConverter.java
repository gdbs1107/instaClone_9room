package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedConverter {
    
    public static Feed toFeed(FeedDTO.FeedPostRequestDTO req, UserEntity user) {
        
        return Feed.builder()
                .content(req.getContent())
                .images(ImageConverter.toImageList(req.getImages()))
                .location(req.getLocation())
                .likesCount(0)
                .commentCount(0)
                .userEntity(user)
                .build();
    }
}
