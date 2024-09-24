package com.example.instaclone_9room.converter.storyConverter;

import com.example.instaclone_9room.controller.dto.storyDTO.HighlightDTO;
import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.storyEntitiy.Highlight;
import com.example.instaclone_9room.domain.storyEntitiy.StoryHighlight;

import java.util.List;
import java.util.stream.Collectors;

public class HighlightConverter {
    
    public static Highlight toHighlight(String text,
                                        UserEntity userEntity) {
        return Highlight.builder()
                .text(text)
                .userEntity(userEntity)
                .build();
    }
    
    public static HighlightDTO.HighlightResponseDTO toHighlightResponseDTO(Highlight highlight,
                                                                         List<StoryHighlight> storyHighlights) {
        List<StoryDTO.StoryResponseDTO> storyResponseDTOS = StoryConverter.toStoryResponseDTOList(
                storyHighlights.stream()
                        .map(StoryHighlight::getStory)
                        .collect(Collectors.toList()));
        return HighlightDTO.HighlightResponseDTO.builder()
                .id(highlight.getId())
                .text(highlight.getText())
                .storyRequestDTOS(storyResponseDTOS)
                .build();
    }
    
}
