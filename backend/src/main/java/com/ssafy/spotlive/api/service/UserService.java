package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.user.UserUpdatePatchReq;
import com.ssafy.spotlive.api.response.user.KakaoUserRes;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.db.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * @FileName : UserService
 * @작성자 : 김민권
 * @Class 설명 : User 관련 비즈니스 로직을 처리하는 Service의 Interface
 */
public interface UserService {
    UserRes insertUser(User newUser);
    UserRes updateUser(UserUpdatePatchReq userUpdatePatchReq);
    UserRes findUserByAccountEmail(String accountEmail);
    UserRes findUserByAccessToken(String accessToken);
}
