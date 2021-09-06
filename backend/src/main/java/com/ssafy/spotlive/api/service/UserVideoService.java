package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.uservideo.UserVideoRes;

/**
 * @FileName : UserVideoService
 * @작성자 : 김민권
 * @Class 설명 : UserVideo 관련 비즈니스 로직을 처리하는 Service의 Interface
 */
public interface UserVideoService {
    UserVideoRes joinUserVideo(String accountEmail, long videoId);
    void leaveUserVideo(String accountEmail, long videoId);
}
