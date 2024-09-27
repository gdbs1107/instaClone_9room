package com.example.instaclone_9room.service.memoService;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.apiPayload.exception.handler.MemoCategoryHandler;
import com.example.instaclone_9room.controller.dto.MemoDTO;
import com.example.instaclone_9room.converter.MemoConverter;
import com.example.instaclone_9room.domain.Memo;
import com.example.instaclone_9room.domain.follow.Follow;
import com.example.instaclone_9room.domain.userEntity.UserEntity;
import com.example.instaclone_9room.repository.MemoRepository;
import com.example.instaclone_9room.repository.followRepository.FollowRepository;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.instaclone_9room.converter.MemoConverter.toMemoCreateResp;


@Service
@Transactional
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService{

    private final MemoRepository memoRepository;

    private final UserRepository userRepository;

    private final FollowRepository followRepository;


    @Override
    public MemoDTO.MemoCreateResp create(MemoDTO.MemoCreateDTO request, String userName) {
        UserEntity findUser = findUser(userName);

        Optional<Memo> existedMemo = memoRepository.findByUserEntity(findUser);

        if (existedMemo.isEmpty()) {
            Memo memo = MemoConverter.toMemo(request, findUser);
            memoRepository.save(memo);

            return new MemoDTO.MemoCreateResp(memo.getId());
        } else {
            throw new MemoCategoryHandler(ErrorStatus.MEMO_EXISTED);
        }


    }

    @Override
    public void delete(String userName, Long memoId) {

        UserEntity findUser = findUser(userName);
        Memo findMemo = findMemo(memoId);

        UserEntity user = findMemo.getUserEntity();


        if (findUser.equals(user)) {

            memoRepository.delete(findMemo);

        } else {
            throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
        }

    }

    @Override
    public void update(String userName, Long memoId, MemoDTO.MemoUpdateDTO request) {

        UserEntity findUser = findUser(userName);
        Memo findMemo = findMemo(memoId);

        UserEntity user = findMemo.getUserEntity();

        if (findUser.equals(user)) {

            findMemo.update(request.getNewContent());

            memoRepository.save(findMemo);
        } else {
            throw new MemberCategoryHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
    }




    @Override
    public MemoDTO.MemoListResponseDTO getMemoList(String userName, int page) {

        UserEntity findUser = findUser(userName);

        List<Follow> follows = followRepository.findByUserEntity(findUser);
        List<UserEntity> followedUsers = follows.stream()
                .map(Follow::getFollowUser)
                .toList();

        Pageable pageable = PageRequest.of(page,5);

        Page<Memo> memoPage = memoRepository.findAllByUserEntityIn(followedUsers, pageable);

        List<MemoDTO.MemoSummaryDTO> memoSummaryDTOS = memoPage.getContent().stream()
                .map(MemoConverter::toMemoSummaryDTO)
                .toList();

        return new MemoDTO.MemoListResponseDTO(
                memoSummaryDTOS,
                memoPage.getTotalPages(),
                memoPage.getTotalElements()
        );
    }

    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new MemberCategoryHandler(ErrorStatus.MEMBER_NOT_FOUND)
        );
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new MemoCategoryHandler(ErrorStatus.MEMO_NOT_FOUND));
    }


    @Scheduled(fixedRate = 3600000) // 일단 1시간 주기로 걸어뒀습니다.
    public void autoDelete() {

        LocalDateTime now = LocalDateTime.now().minusHours(24); // 현 시점으로부터 24시간 전

        List<Memo> expiredMemos = memoRepository.findByUpdatedAtBefore(now);

        memoRepository.deleteAll(expiredMemos);
    }


}

