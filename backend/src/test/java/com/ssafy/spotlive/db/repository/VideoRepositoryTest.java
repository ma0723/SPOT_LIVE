package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.timetable.TimetableInsertPostReq;
import com.ssafy.spotlive.api.request.video.VideoInsertPostReq;
import com.ssafy.spotlive.db.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @FileName : VideoRepositoryTest
 * @작성자 : 권영린
 * @Class 설명 : Video 테스트를 위한 Test클래스
 */
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VideoRepositoryTest {
    static final int MAX = 2147483647;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    TimetableRepository timetableRepository;

    static String testAccountEmail1 = "test_account_1@gmail.com";
    static User user = new User();
    static Category category = new Category();
    static ShowInfo showInfo = new ShowInfo();
    static Video video1 = new Video();
    static Video video2 = new Video();

    @BeforeAll
    void initTestData() {
        //첫번 째 유저 추가
        user.setAccountEmail(testAccountEmail1);
        user.setGender("테스트MAN");
        user.setAgeRange("테스트10~19");
        user.setProfileDescription("테스트프로필");
        user.setUserName("테스트이름");
        user.setPhoneNumber("테스트전화");
        user.setProfileImageUrl("테스트URL");

        category.setCategoryName("카테고리");

        showInfo.setShowInfoTitle("테스트공연제목");
        showInfo.setShowInfoDescription("테스트공연내용");
        showInfo.setUser(user);
        showInfo.setPosterUrl("테스트포스터URL");
        showInfo.setPrice(4000L);
        showInfo.setRunningTime(300);

        video1.setVideoTitle("테스트비디오1");
        video1.setVideoDescription("테스트내용1");
        video1.setShowInfo(showInfo);
        video1.setThumbnailUrl("테스트썸네일1");
        video1.setMode("테스트소통1");
        video1.setCategory(category);
        video1.setUser(user);

        video2.setVideoTitle("테스트비디오2");
        video2.setVideoDescription("테스트내용2");
        video2.setShowInfo(showInfo);
        video2.setThumbnailUrl("테스트썸네일2");
        video2.setMode("테스트소통2");
        video2.setCategory(category);
        video2.setUser(user);
    }

    @Test
    void findPageVideosByMode(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;

        Long categoryId = 2L;
        String mode = "소통";
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByMode(pageRequest, mode);
        Page<Video> pageVideo2 = videoRepository.findVideosByModeAndCategory_CategoryId(pageRequest, mode, categoryId);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
        assertThat(pageVideo2.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findPageVideosByIsLive(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;
        Boolean isLive = true;
        Sort sort = Sort.by(Sort.Direction.DESC, "hit");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByIsLiveOrderByHitDesc(pageRequest, isLive);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByIsLiveAndCategory_CategoryId(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;
        Boolean isLive = true;
        Long categoryId = 2L;
        Sort sort = Sort.by(Sort.Direction.DESC, "hit");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, isLive, categoryId);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findPageVideosByUser_AccountEmailIn(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;
        List<String> accountEmailList = new ArrayList<>();
        accountEmailList.add("sqk8657@naver.com");
        accountEmailList.add("ahyen@naver.com");
        accountEmailList.add("emoney96@naver.com");
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByUser_AccountEmailIn(pageRequest, accountEmailList);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByCategory_CategoryIdAndUser_AccountEmailIn(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;
        List<String> accountEmailList = new ArrayList<>();
        accountEmailList.add("sqk8657@naver.com");
        accountEmailList.add("ahyen@naver.com");
        accountEmailList.add("emoney96@naver.com");
        Long categoryId = 2L;
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByCategory_CategoryIdAndUser_AccountEmailIn(pageRequest, categoryId, accountEmailList);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByUserAccountEmail() {
        // given
        userRepository.save(user);
        categoryRepository.save(category);
        showInfoRepository.save(showInfo);
        videoRepository.save(video1);
        videoRepository.save(video2);

        //when
        List<Video> videosByUserAccountEmail = videoRepository.findVideosByUserAccountEmail(testAccountEmail1);

        //then
        boolean isPresentTest2 = videosByUserAccountEmail.stream().anyMatch(video -> video.getThumbnailUrl().contains("테스트썸네일2"));
        boolean isPresentTest3 = videosByUserAccountEmail.stream().anyMatch(video -> video.getThumbnailUrl().contains("테스트썸네일3"));
        assertThat(isPresentTest2).isEqualTo(true);
        assertThat(isPresentTest3).isEqualTo(false);
    }

    @Test
    void findVideosByMode(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        String mode = "소통";

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        List<Video> videoList = videoRepository.findVideosByMode(mode).orElse(null);

        // then
        assertThat(videoList.stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByIsLive(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        Boolean isLive = true;

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        List<Video> videoList = videoRepository.findVideosByIsLive(isLive).orElse(null);

        // then
        assertThat(videoList.stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByUser_AccountEmailIn(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        List<String> accountEmailList = new ArrayList<>();
        accountEmailList.add("emoney96@naver.com");

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        List<Video> videoList = videoRepository.findVideosByUser_AccountEmailIn(accountEmailList).orElse(null);

        // then
        assertThat(videoList.stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "소통";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        int page = 0;
        int size = MAX;
        String videoTitle = "kmk";
        String videoDescription = "kmk";
        String profileNickname = "kmk";
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        Page<Video> pageVideo = videoRepository.findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(pageRequest, videoTitle, videoDescription, profileNickname);

        // then
        assertThat(pageVideo.getContent().stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }

    @Test
    void findVideosByModeAndTimetable_TimetableIdIn(){
        // given
        String insertVideoTitle = "알고리즘 잘하는법";
        String insertVideoDescription = "kmk님이 알려주실겁니다";
        String insertMode = "공연";
        Long insertCategoryId = 2L;
        String insertAccountEmail = "emoney96@naver.com";
        String insertSessionId = "123123";
        String insertThumbnailUrl = "solvedac.png";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        VideoInsertPostReq videoInsertPostReq = new VideoInsertPostReq();
        videoInsertPostReq.setVideoTitle(insertVideoTitle);
        videoInsertPostReq.setVideoDescription(insertVideoDescription);
        videoInsertPostReq.setMode(insertMode);
        videoInsertPostReq.setCategoryId(insertCategoryId);
        videoInsertPostReq.setShowInfoId(showInfo.getShowInfoId());
        videoInsertPostReq.setTimetableId(timetable.getTimetableId());
        videoInsertPostReq.setAccountEmail(insertAccountEmail);
        videoInsertPostReq.setSessionId(insertSessionId);

        List<Long> timetableIdList = new ArrayList<>();
        timetableIdList.add(timetable.getTimetableId());

        String mode = "공연";

        // when
        Video video = videoRepository.save(videoInsertPostReq.toVideo(insertThumbnailUrl));
        List<Video> videoList = videoRepository.findVideosByModeAndTimetable_TimetableIdIn(mode, timetableIdList).orElse(null);

        // then
        assertThat(videoList.stream().anyMatch(newVideo -> newVideo.getVideoId() == video.getVideoId())).isEqualTo(true);
    }
}
