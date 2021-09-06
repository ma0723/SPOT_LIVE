package com.ssafy.spotlive.api.request.uservideo;

import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.entity.UserVideo;
import com.ssafy.spotlive.db.entity.UserVideoId;
import com.ssafy.spotlive.db.entity.Video;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : UserVideoReq
 * @작성자 : 김민권
 * @Class 설명 : User의 Video 참여를 전달하는 Req DTO
 */
@Getter
@Setter
@ToString
public class UserVideoReq {
    String accountEmail;
    Long videoId;

    public UserVideo toUserVideo() {
        /**
         * @Method Name : toUserVideo
         * @작성자 : 김민권
         * @Method 설명 :  UserVideoReq를 UserVideo 객체로 변환하는 메소드
         */
        User user = new User();
        user.setAccountEmail(this.accountEmail);
        Video video = new Video();
        video.setVideoId(this.videoId);

        UserVideo userVideo = new UserVideo();
        userVideo.setUser(user);
        userVideo.setVideo(video);

        return userVideo;
    }

    public UserVideoId toUserVideoId() {
        /**
         * @Method Name : toUserVideoId
         * @작성자 : 김민권
         * @Method 설명 :  UserVideoReq를 UserVideoId 객체로 변환하는 메소드
         */
        UserVideoId userVideoId = new UserVideoId();
        userVideoId.setUser(this.accountEmail);
        userVideoId.setVideo(this.videoId);

        return userVideoId;
    }
}
