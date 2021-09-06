package com.ssafy.spotlive.api.response.follow;

import com.ssafy.spotlive.db.entity.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @FileName : FollowRes
 * @작성자 : 권영린
 * @Class 설명 : 카테고리 조회 API 요청에 대한 CategoryResponse 정의
 */
@Getter
@Setter
@Builder
public class FollowMyFanRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;

    public static FollowMyFanRes of(Follow follow){
        /**
         * @Method Name : of
         * @작성자 : 권영린
         * @Method 설명 : Category Entity를 CategoryGetResponseDto로 변환하는 메소드
         */
        return FollowMyFanRes.builder()
                .accountEmail(follow.getFan().getAccountEmail())
                .profileNickname(follow.getFan().getProfileNickname())
                .profileImageUrl(follow.getFan().getProfileImageUrl())
                .build();
    }
}
