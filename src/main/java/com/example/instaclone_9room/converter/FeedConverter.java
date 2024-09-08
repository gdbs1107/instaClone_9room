package com.example.instaclone_9room.converter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.Image;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FeedConverter {
    
    public static Feed toFeed(FeedDTO.FeedPostRequestDTO req, UserEntity user) {
        
        List<Image> images = ImageConverter.toImageList(req.getImageDTOS());
        
        Feed newFeed = Feed.builder()
                .content(req.getContent())
                .images(images)
                .location(req.getLocation())
                .likesCount(0)
                .commentCount(0)
                .userEntity(user)
                .build();
        
        newFeed.setImages(images);
        
        return newFeed;
    }
}
