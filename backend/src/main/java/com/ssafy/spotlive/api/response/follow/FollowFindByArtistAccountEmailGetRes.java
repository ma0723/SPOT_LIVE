package com.ssafy.spotlive.api.response.follow;

import com.ssafy.spotlive.db.entity.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @FileName : FollowFindByArtistAccountEmailGetRes
 * @작성자 : 권영린
 * @Class 설명 : 자신을 팔로우하는 팬 리스트에 담길 Response
 */
@Getter
@Setter
@Builder
public class FollowFindByArtistAccountEmailGetRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;

    public static FollowFindByArtistAccountEmailGetRes of(Follow follow) {
        return FollowFindByArtistAccountEmailGetRes.builder()
                .accountEmail(follow.getFan().getAccountEmail())
                .profileNickname(follow.getFan().getProfileNickname())
                .profileImageUrl(follow.getFan().getProfileImageUrl())
                .build();
    }
}
