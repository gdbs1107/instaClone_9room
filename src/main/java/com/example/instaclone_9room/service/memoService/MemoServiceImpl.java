package com.example.instaclone_9room.service.memoService;

import com.example.instaclone_9room.controller.dto.MemoDTO;
import com.example.instaclone_9room.domain.Memo;
import com.example.instaclone_9room.domain.UserEntity;
import com.example.instaclone_9room.repository.MemoRepository;
import com.example.instaclone_9room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService{

    private final MemoRepository memoRepository;

    private final UserRepository userRepository;


    @Override
    public void create(MemoDTO.MemoCreateDTO request, String userName) {
        UserEntity findUser = findUser(userName);
    }

    @Override
    public void delete(String userName, Long memoId) {

    }

    @Override
    public void update(String userName, Long memoId, MemoDTO.MemoUpdateDTO request) {

    }

    @Override
    public MemoDTO.MemoListResponseDTO getMemoList(String userName, int page) {
        return null;
    }

    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new ReelsCategoryHandler(ErrorStatus.REELS_NOT_FOUND));
    }
}


}
