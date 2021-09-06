//package com.ssafy.spotlive.api.service;
//
//import com.ssafy.spotlive.api.response.video.VideoFindAllByUserIdGetRes;
//import com.ssafy.spotlive.api.response.video.VideoOpenViduSessionGetRes;
//import com.ssafy.spotlive.db.repository.VideoRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
////import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@Transactional
//class VideoServiceImplTest {
//
//    @Autowired
//    VideoService videoService;
//    @Autowired
//    VideoRepository videoRepository;
//
//    @Test
//    void 오픈비두_세션생성테스트() {
//        // given
//
//        // when
//        String sessionId = videoService.createSession();
//
//        // then
//        assertThat(sessionId).contains("session");
//    }
//
//    @Test
//    void 오픈비두_토큰발급테스트() {
//        // given
//        String sessionId = videoService.createSession();
//
//        // when
//        VideoOpenViduSessionGetRes videoOpenViduSessionGetRes = videoService.createToken(sessionId);
//
//        // then
//        assertThat(videoOpenViduSessionGetRes.getToken()).contains("wss://i5a405.p.ssafy.io");
//        assertThat(videoOpenViduSessionGetRes.getSessionId()).isEqualTo(sessionId);
//    }
//
//    @Test
//    void 오픈비두_세션종료테스트() {
//        // given
//        String sessionId = videoService.createSession();
//
//        // when
//        int statusCodeForSuccess = videoService.closeSession(sessionId);
//        int statusCodeForFail = videoService.closeSession(sessionId);
//
//        // then
//        assertThat(statusCodeForSuccess).isEqualTo(204);
//        assertThat(statusCodeForFail).isEqualTo(404);
//    }
//
//    void 특정유저영상조회테스트() {
//        String accountEmail = "sqk8657@naver.com";
//        List<VideoFindAllByUserIdGetRes> videoByAccountEmail = videoService.findVideoByAccountEmail(accountEmail);
//        assertThat(videoByAccountEmail.size()).isEqualTo(9);
//    }
//}