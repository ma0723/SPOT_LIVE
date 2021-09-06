package com.ssafy.spotlive.api.response.follow;

import com.ssafy.spotlive.db.entity.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @FileName : FollowFindByFanAccountEmailGetRes
 * @작성자 : 권영린
 * @Class 설명 : 팬이 팔로우하는 아티스트 리스트에 담길 Response
 */
@Getter
@Setter
@Builder
public class FollowFindByFanAccountEmailGetRes {
    String accountEmail;
    String profileNickname;
    String profileImageUrl;

    public static FollowFindByFanAccountEmailGetRes of(Follow follow) {
        return FollowFindByFanAccountEmailGetRes.builder()
                .accountEmail(follow.getArtist().getAccountEmail())
                .profileNickname(follow.getArtist().getProfileNickname())
                .profileImageUrl(follow.getArtist().getProfileImageUrl())
                .build();
    }
}
