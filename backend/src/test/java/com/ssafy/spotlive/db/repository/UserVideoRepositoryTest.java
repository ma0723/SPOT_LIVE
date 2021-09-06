//package com.ssafy.spotlive.db.repository;
//
//
//import com.ssafy.spotlive.db.entity.User;
//import com.ssafy.spotlive.db.entity.UserVideo;
//import com.ssafy.spotlive.db.entity.UserVideoId;
//import com.ssafy.spotlive.db.entity.Video;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@SpringBootTest // 복합키를 통한 Repository 생성 시, JpaRespository 상속이 올바르게 되었는지 기본 기능으로 테스트해본다.
//class UserVideoRepositoryTest {
//
//    @Autowired
//    UserVideoRepository userVideoRepository;
//
//    @Test
//    void findAllTest() {
//        // given
//        // when
//        List<UserVideo> userVideoList = userVideoRepository.findAll();
//
//        // then
//        assertThat(userVideoList.size()).isEqualTo(3);
//    }
//
//    @Test
//    void findByIdTest() {
//        // given
//        String accountEmail = "emoney96@naver.com";
//        long videoId = 8;
//        UserVideoId userVideoId = new UserVideoId();
//        userVideoId.setVideo(videoId);
//        userVideoId.setUser(accountEmail);
//
//        // when
//        Optional<UserVideo> optionalUserVideo = userVideoRepository.findById(userVideoId);
//
//        // then
//        assertThat(optionalUserVideo).isNotEqualTo(Optional.empty());
//        assertThat(optionalUserVideo.get().getUser().getAccountEmail()).isEqualTo(accountEmail);
//        assertThat(optionalUserVideo.get().getVideo().getVideoId()).isEqualTo(videoId);
//    }
//
//    @Test
//    void findAllByUserTest() {
//        // given
//        String accountEmail = "emoney96@naver.com";
//
//        // when
//        List<UserVideo> allByUser = userVideoRepository.findAllByUser_AccountEmail(accountEmail);
//
//        // then
//        allByUser.stream().forEach(userVideo -> assertThat(userVideo.getUser().getAccountEmail()).isEqualTo(accountEmail));
//    }
//
//    @Test
//    void findAllByVideoIdTest() {
//        // given
//        long videoId = 8L;
//
//        // when
//        List<UserVideo> allByVideo = userVideoRepository.findAllByVideo_VideoId(videoId);
//
//        // then
//        allByVideo.stream().forEach(userVideo -> assertThat(userVideo.getVideo().getVideoId()).isEqualTo(videoId));
//    }
//
//    @Test
//    @Transactional
//    void saveTest() {
//        // given
//        String accountEmail = "emoney96@naver.com";
//        long videoId = 2;
//
//        User user = new User();
//        user.setAccountEmail(accountEmail);
//
//        Video video = new Video();
//        video.setVideoId(videoId);
//
//        UserVideo userVideo = new UserVideo();
//        userVideo.setUser(user);
//        userVideo.setVideo(video);
//
//        // when
//        userVideoRepository.save(userVideo);
//
//        UserVideoId userVideoId = new UserVideoId();
//        userVideoId.setUser(accountEmail);
//        userVideoId.setVideo(videoId);
//
//        Optional<UserVideo> expectResult = userVideoRepository.findById(userVideoId);
//
//        // then
//        assertThat(expectResult).isNotEqualTo(Optional.empty());
//        assertThat(expectResult.get().getUser().getAccountEmail()).isEqualTo(accountEmail);
//        assertThat(expectResult.get().getVideo().getVideoId()).isEqualTo(videoId);
//
//        assertThat(expectResult.get().getUser().getUserName()).isEqualTo("강용수");
//        assertThat(expectResult.get().getVideo().getVideoTitle()).contains("심심해요");
//    }
//}