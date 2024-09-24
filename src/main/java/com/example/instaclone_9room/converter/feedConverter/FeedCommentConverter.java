package com.example.instaclone_9room.converter.feedConverter;

import com.example.instaclone_9room.controller.dto.feedDTO.FeedCommentDTO;
import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedComment;

public class FeedCommentConverter {
    public static FeedComment toFeedComment(FeedCommentDTO.CommentPostRequestDTO request, UserEntity userEntity, Feed feed) {
        return FeedComment.builder()
                .content(request.getContent())
                .userEntity(userEntity)
                .feed(feed)
                .build();
    }
}
