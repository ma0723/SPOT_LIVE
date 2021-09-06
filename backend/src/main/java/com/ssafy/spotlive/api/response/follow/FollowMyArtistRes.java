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
public class FollowMyArtistRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;

    public static FollowMyArtistRes of(Follow follow){
        /**
         * @Method Name : of
         * @작성자 : 권영린
         * @Method 설명 : Category Entity를 CategoryGetResponseDto로 변환하는 메소드
         */
        return FollowMyArtistRes.builder()
                .accountEmail(follow.getArtist().getAccountEmail())
                .profileNickname(follow.getArtist().getProfileNickname())
                .profileImageUrl(follow.getArtist().getProfileImageUrl())
                .build();
    }
}
