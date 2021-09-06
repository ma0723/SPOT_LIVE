package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.uservideo.UserVideoRes;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.entity.UserVideo;
import com.ssafy.spotlive.db.entity.Video;
import com.ssafy.spotlive.db.repository.UserVideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserVideoServiceImplTest {

    @Mock
    private UserVideoRepository userVideoRepository;

    @InjectMocks
    private UserVideoServiceImpl userVideoService;

    @Test
    @Transactional
    void joinUserVideo() {
        // given
        String accountEmail = "kmk130519@naver.com";
        long videoId = 9L;

        User user = new User();
        user.setAccountEmail(accountEmail);
        Video video = new Video();
        video.setVideoId(videoId);

        UserVideo returnUserVideo = new UserVideo();
        returnUserVideo.setUser(user);
        returnUserVideo.setVideo(video);

        doReturn(returnUserVideo).when(userVideoRepository).save(anyObject());

        // when
        UserVideoRes expectUserVideoRes = userVideoService.joinUserVideo(accountEmail, videoId);

        // then
        verify(userVideoRepository).save(anyObject());
        assertThat(expectUserVideoRes.getAccountEmail()).isEqualTo(accountEmail);
        assertThat(expectUserVideoRes.getVideoId()).isEqualTo(videoId);
    }

    @Test
    @Transactional
    void leaveUserVideo() {
        // given
        String accountEmail = "emoney96@naver.com";
        long videoId = 2L;

        // when
        userVideoService.leaveUserVideo(accountEmail, videoId);

        // then
        verify(userVideoRepository).deleteById(anyObject());
    }
}