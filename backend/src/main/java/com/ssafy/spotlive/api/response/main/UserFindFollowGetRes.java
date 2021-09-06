package com.ssafy.spotlive.api.response.main;

import com.ssafy.spotlive.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : UserFindFollowGetRes
 * @작성자 : 강용수
 * @Class 설명 : 메인 화면 사이드바의 내 구독자 채널 리스트 조회 요청에 대한 UserFindFollowGetResponseDto 정의
 */
@Getter
@Setter
@Builder
@ToString
public class UserFindFollowGetRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;
    Boolean isLive;

    public static UserFindFollowGetRes of(User user){
        /**
         * @Method Name : of
         * @작성자 : 강용수
         * @Method 설명 : User Entity를 UserFindFollowGetResponseDto로 변환하는 메소드
         */
        return UserFindFollowGetRes.builder()
                .accountEmail(user.getAccountEmail())
                .profileNickname(user.getProfileNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .isLive(user.getVideoList().stream().anyMatch(video -> video.getIsLive() == true))
                .build();
    }
}