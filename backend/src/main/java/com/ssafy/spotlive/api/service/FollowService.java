package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.follow.FollowFindByArtistAccountEmailGetRes;
import com.ssafy.spotlive.api.response.follow.FollowFindByFanAccountEmailGetRes;

import java.util.List;

/**
 * @FileName : FollowService
 * @작성자 : 권영린
 * @Class 설명 : 팔로우 관련 기능을 위한 Service 정의.
 */
public interface FollowService {
    void insertFollowByAccountEmail(String artistEmail, String fanEmail);
    void deleteFollowByAccountEmail(String accessToken, String artistEmail);
    List<FollowFindByFanAccountEmailGetRes> findArtistByFanAccountEmail(String fanEmail);
    List<FollowFindByArtistAccountEmailGetRes> findFanByArtistAccountEmail(String artistEmail);
}
