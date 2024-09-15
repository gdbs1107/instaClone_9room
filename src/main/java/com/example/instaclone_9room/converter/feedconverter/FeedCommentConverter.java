package com.example.instaclone_9room.converter.feedconverter;

import com.example.instaclone_9room.controller.dto.postDTO.FeedCommentDTO;
import com.example.instaclone_9room.domain.UserEntity;
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
