package com.ssafy.spotlive.api.response.uservideo;

import com.ssafy.spotlive.db.entity.UserVideo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : UserVideoRes
 * @작성자 : 김민권
 * @Class 설명 : User의 Video 참여를 전달하는 Res DTO
 */
@Getter
@Setter
@ToString
@Builder
public class UserVideoRes {

    String accountEmail;
    Long videoId;

    public static UserVideoRes of(UserVideo userVideo) {
        /**
         * @Method Name : of
         * @작성자 : 김민권
         * @Method 설명 :  UserVideo를 UserVideoRes 객체로 변환하는 메소드
         */
        return UserVideoRes.builder()
                .accountEmail(userVideo.getUser().getAccountEmail())
                .videoId(userVideo.getVideo().getVideoId())
                .build();
    }
}
