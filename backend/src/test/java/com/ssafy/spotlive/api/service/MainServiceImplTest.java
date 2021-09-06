package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.db.entity.Follow;
import com.ssafy.spotlive.db.entity.Reservation;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.entity.Video;
import com.ssafy.spotlive.db.repository.ReservationRepository;
import com.ssafy.spotlive.db.repository.UserRepository;
import com.ssafy.spotlive.db.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainServiceImplTest {
    static final int MAX = 2147483647;
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Page<Video> pageVideo;

    @InjectMocks
    private MainServiceImpl mainServiceImpl;

    @Test
    void findAllVideoByModeAndCategoryId() {
        // given
        int page = 0;
        int size = MAX;
        Long categoryId1 = 6L;
        Long categoryId2 = null;
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        String mode = "소통";

        // when
        when(videoRepository.findVideosByMode(pageRequest, mode)).thenReturn(pageVideo);
        when(videoRepository.findVideosByModeAndCategory_CategoryId(pageRequest, mode, categoryId1)).thenReturn(pageVideo);
        mainServiceImpl.findAllVideoByModeAndCategoryId(page, size, categoryId1, mode);
        mainServiceImpl.findAllVideoByModeAndCategoryId(page, size, categoryId2, mode);

        // then
        verify(videoRepository).findVideosByMode(pageRequest, mode);
        verify(videoRepository).findVideosByModeAndCategory_CategoryId(pageRequest, mode, categoryId1);
    }

    @Test
    void findAllReplayVideoByIsLiveAndCategoryId() {
        // given
        int page = 0;
        int size = MAX;
        Long categoryId1 = 6L;
        Long categoryId2 = null;
        Boolean isLive = false;
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        when(videoRepository.findVideosByIsLiveOrderByHitDesc(pageRequest, isLive)).thenReturn(pageVideo);
        when(videoRepository.findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, isLive, categoryId1)).thenReturn(pageVideo);
        mainServiceImpl.findAllReplayVideoByIsLiveAndCategoryId(page, size, categoryId1);
        mainServiceImpl.findAllReplayVideoByIsLiveAndCategoryId(page, size, categoryId2);

        // then
        verify(videoRepository).findVideosByIsLiveOrderByHitDesc(pageRequest, isLive);
        verify(videoRepository).findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, isLive, categoryId1);
    }

    @Test
    void findAllLiveVideoByIsLiveAndCategoryId() {
        // given
        int page = 0;
        int size = MAX;
        Long categoryId1 = 6L;
        Long categoryId2 = null;
        Boolean isLive = true;
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        when(videoRepository.findVideosByIsLiveOrderByHitDesc(pageRequest, isLive)).thenReturn(pageVideo);
        when(videoRepository.findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, isLive, categoryId1)).thenReturn(pageVideo);
        mainServiceImpl.findAllLiveVideoByIsLiveAndCategoryId(page, size, categoryId1);
        mainServiceImpl.findAllLiveVideoByIsLiveAndCategoryId(page, size, categoryId2);

        // then
        verify(videoRepository).findVideosByIsLiveOrderByHitDesc(pageRequest, isLive);
        verify(videoRepository).findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, isLive, categoryId1);
    }

    @Test
    void findAllFollowVideoByCategoryId() {
        // given
        int page = 0;
        int size = MAX;
        Long categoryId1 = 6L;
        Long categoryId2 = null;
        String accountEmail = "emoney96@naver.com";
        List<String> accountEmailList = new ArrayList<>();

        User user = new User();
        user.setAccountEmail("emoney96@naver.com");
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Follow follow = new Follow();
        follow.setFan(user);
        user.setAccountEmail("ahyen@naver.com");
        follow.setArtist(user);

        // when
        when(videoRepository.findVideosByUser_AccountEmailIn(pageRequest, accountEmailList)).thenReturn(pageVideo);
        when(videoRepository.findVideosByCategory_CategoryIdAndUser_AccountEmailIn(pageRequest, categoryId1, accountEmailList)).thenReturn(pageVideo);
        when(userRepository.findById(accountEmail)).thenReturn(Optional.ofNullable(user));
        mainServiceImpl.findAllFollowVideoByCategoryId(page, size, categoryId1, accountEmail);
        mainServiceImpl.findAllFollowVideoByCategoryId(page, size, categoryId2, accountEmail);

        // then
        verify(videoRepository).findVideosByUser_AccountEmailIn(pageRequest, accountEmailList);
        verify(videoRepository).findVideosByCategory_CategoryIdAndUser_AccountEmailIn(pageRequest, categoryId1, accountEmailList);
    }

    @Test
    void findAllFollowByFan(){
        // given
        String accountEmail = "emoney96@naver.com";
        User user = new User();

        // when
        when(userRepository.findById(accountEmail)).thenReturn(Optional.ofNullable(user));
        mainServiceImpl.findAllFollowByFan(accountEmail);

        // then
        verify(userRepository).findById(accountEmail);
    }

    @Test
    void findAllSearchVideoByKeywordContains(){
        // given
        int page = 0;
        int size = MAX;
        String keyword = "1";
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        when(videoRepository.findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(pageRequest, keyword, keyword, keyword)).thenReturn(pageVideo);
        mainServiceImpl.findAllSearchVideoByKeywordContains(page, size, keyword);

        // then
        verify(videoRepository).findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(pageRequest, keyword, keyword, keyword);
    }

    @Test
    void findAllReservationVideoByModeAndTimetableIdIn(){
        // given
        String mode = "공연";
        String accountEmail = "emoney96@naver.com";
        List<Reservation> reservationList = new ArrayList<>();
        List<Long> timetableIdList = new ArrayList<>();
        List<Video> videoList = new ArrayList<>();

        // when
        when(reservationRepository.findReservationByUser_AccountEmail(accountEmail)).thenReturn(Optional.ofNullable(reservationList));
        when(videoRepository.findVideosByModeAndTimetable_TimetableIdIn(mode, timetableIdList)).thenReturn(Optional.ofNullable(videoList));
        mainServiceImpl.findAllReservationVideoByModeAndTimetableIdIn(mode, accountEmail);

        // then
        verify(reservationRepository).findReservationByUser_AccountEmail(accountEmail);
        verify(videoRepository).findVideosByModeAndTimetable_TimetableIdIn(mode, timetableIdList);
    }
}