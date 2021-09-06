package com.ssafy.spotlive.api.response.main;

import com.ssafy.spotlive.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : VideoFindUserRes
 * @작성자 : 강용수
 * @Class 설명 : 메인 화면 내 Video 송출자 정보용 Dto
 */
@Getter
@Setter
@Builder
@ToString
public class VideoFindUserRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;

    public static VideoFindUserRes of(User user){
        /**
         * @Method Name : of
         * @작성자 : 강용수
         * @Method 설명 : User Entity를 VideoFindUserResponseDto로 변환하는 메소드
         */
        return VideoFindUserRes.builder()
                .accountEmail(user.getAccountEmail())
                .profileNickname(user.getProfileNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}