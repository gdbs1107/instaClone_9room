package com.example.instaclone_9room.converter.storyConverter;

import com.example.instaclone_9room.domain.storyEntitiy.Highlight;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import com.example.instaclone_9room.domain.storyEntitiy.StoryHighlight;

public class StoryHighlightConverter {
    
    public static StoryHighlight toStoryHighlight(Story story, Highlight highlight) {
        return StoryHighlight.builder()
                .story(story)
                .highlight(highlight)
                .build();
    }
}
