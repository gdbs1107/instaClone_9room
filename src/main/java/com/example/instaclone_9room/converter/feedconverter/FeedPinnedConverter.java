package com.example.instaclone_9room.converter.feedconverter;

import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedPinned;

public class FeedPinnedConverter {
    
    public static FeedPinned toFeedPinned(UserEntity userEntity, Feed feed) {
        
        return FeedPinned.builder()
                .userEntity(userEntity)
                .feed(feed)
                .build();
    }
}
