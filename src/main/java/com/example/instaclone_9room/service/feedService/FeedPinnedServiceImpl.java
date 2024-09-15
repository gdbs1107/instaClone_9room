package com.example.instaclone_9room.service.feedService;

import com.example.instaclone_9room.controller.dto.postDTO.FeedDTO;
import com.example.instaclone_9room.converter.feedconverter.FeedConverter;
import com.example.instaclone_9room.converter.feedconverter.FeedPinnedConverter;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.domain.feedEntity.Feed;
import com.example.instaclone_9room.domain.feedEntity.FeedPinned;
import com.example.instaclone_9room.repository.UserRepository;
import com.example.instaclone_9room.repository.postRepository.FeedPinnedRepository;
import com.example.instaclone_9room.repository.postRepository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedPinnedServiceImpl implements FeedPinnedService {
    
    private final FeedPinnedRepository feedPinnedRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    
    @Override
    public void savePinnedFeed(String username, Long feedId) {
        
        UserEntity findUser = findUser(username);
        Feed findFeed = findFeed(feedId);
        
        FeedPinned feedPinnedByUserEntity = feedPinnedRepository.findByUserEntityAndFeed(findUser, findFeed)
                .orElse(null);
        
        if (feedPinnedByUserEntity!=null){
            
            findFeed.removePinCount();
            feedPinnedRepository.delete(feedPinnedByUserEntity);
            
        }else {
            FeedPinned feedPinned = FeedPinnedConverter.toFeedPinned(findUser, findFeed);
            findFeed.addPinCount();
            feedPinnedRepository.save(feedPinned);
        }
    }
    
    @Override
    public List<FeedDTO.FeedResponseDTO> getPinnedFeed(String username) {
        
        UserEntity user = findUser(username); // 요청한 유저 정보를 가져옴
        List<FeedPinned> feedPinnedList = feedPinnedRepository.findByUserEntity(user);
        
        
        return feedPinnedList.stream()
                .map(feedPinned -> {
                    Feed feed = feedPinned.getFeed();
                    UserEntity userEntity = feed.getUserEntity();
                    return FeedConverter.toFeedResponseDTO(feed);
                })
                .collect(Collectors.toList());
    }
    
    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
    
    private Feed findFeed(Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(
                () -> new RuntimeException("Feed not found"));
    }
}
