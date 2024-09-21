package com.example.instaclone_9room.service.storyService;

import com.example.instaclone_9room.controller.dto.storyDTO.HighlightDTO;
import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.converter.storyConverter.HighlightConverter;
import com.example.instaclone_9room.converter.storyConverter.StoryConverter;
import com.example.instaclone_9room.converter.storyConverter.StoryHighlightConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.storyEntitiy.Highlight;
import com.example.instaclone_9room.domain.storyEntitiy.Story;
import com.example.instaclone_9room.domain.storyEntitiy.StoryHighlight;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.storyRepository.HighlightRepository;
import com.example.instaclone_9room.repository.storyRepository.StoryHighlightRepository;
import com.example.instaclone_9room.repository.storyRepository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HighlightServiceImpl implements HighlightService {
    
    private final HighlightRepository highlightRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final StoryHighlightRepository storyHighlightRepository;
    
    @Override
    public void createHighlight(HighlightDTO.HighlightPostRequestDTO requestDTO, String username) {
        UserEntity user = findUser(username);
        
        List<Story> stories = getStories(requestDTO.getStoryRequestDTOS());
        
        Highlight highlight = HighlightConverter.toHighlight(requestDTO.getText(), user);
        
        List<StoryHighlight> storyHighlights = stories.stream()
                .peek(story -> userValidate(story, user))
                .map(story -> StoryHighlightConverter.toStoryHighlight(story, highlight))
                .toList();
        
        highlightRepository.save(highlight);
        storyHighlightRepository.saveAll(storyHighlights);
    }
    
    @Override
    public void deleteHighlight(Long highlightId) {
        
        Highlight highlight = highlightRepository.findById(highlightId).orElseThrow(
                () -> new RuntimeException("Highlight not found")
        );
        
        List<StoryHighlight> storyHighlights = storyHighlightRepository.findAllByHighlight(highlight);
        
        storyHighlightRepository.deleteAll(storyHighlights);
        highlightRepository.delete(highlight);
    }
    
    @Override
    public HighlightDTO.HighlightResponseDTO getHighlight(Long highlightId) {
        
        Highlight highlight = findHighlight(highlightId);
        
        List<StoryHighlight> storyHighlights = storyHighlightRepository.findAllByHighlight(highlight);
        
        
        HighlightDTO.HighlightResponseDTO responseDTO = HighlightConverter.toHighlightResponseDTO(highlight, storyHighlights);
        
        return responseDTO;
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
    
    private Highlight findHighlight(Long highlightId) {
        return highlightRepository.findById(highlightId).orElseThrow(
                () -> new RuntimeException("Highlight not found"));
    }
    
    private static void userValidate(Story story, UserEntity user) {
        if (!story.getUserEntity().equals(user)) {
            throw new RuntimeException("no permission");
        }
    }
    
    private List<Story> getStories(List<StoryDTO.StoryRequestDTO> storyRequestDTOS) {
        return storyRequestDTOS.stream()
                .map(storyRequestDTO -> storyRepository.findByFileName(storyRequestDTO.getFileName()).orElseThrow(
                        () -> new RuntimeException("Story not found")
                ))
                .toList();
    }
}
