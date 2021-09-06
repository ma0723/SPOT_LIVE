package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.request.user.UserUpdatePatchReq;
import com.ssafy.spotlive.api.response.user.KakaoUserRes;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @FileName : UserController
 * @작성자 : 김민권
 * @Class 설명 : OAuth, User 관련 인증을 매핑하는 REST Controller
 */
@Api(value = "User 및 인증 API", tags = {"UserController"}, description = "유저 관련 API를 매핑하는 컨트롤러")
@RestController
@RequestMapping("/api/auth/")
@CrossOrigin( value = {"*"}, maxAge = 6000)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    private final String EMAIL_SUFFIX = "@gmail.com";

    @GetMapping("/kakao/showlogin")
    @ApiOperation(value = "카카오 로그인을 위한 요청 URL 전송", notes = "카카오 로그인을 위한 요청 URL을 전송한다. 해당 URL로 GET 요청을 보내면 된다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적인 URL 전송 완료"),
    })
    public ResponseEntity<String> showLogin() {
        /**
         * @Method Name : showLogin
         * @작성자 : 김민권
         * @Method 설명 : 카카오 로그인을 위한 요청 URL을 전송하는 Method, 해당 URL로 GET 요청을 전송 시 카카오톡 로그인 페이지로 이동된다.
         */
        return new ResponseEntity<>(authService.getKakaoLoginUrl(), HttpStatus.OK);
    }

    @GetMapping("/kakao/login")
    @ApiOperation(value = "kakao token을 얻어와 유저정보 조회 후, 회원가입 혹은 로그인 수행 후 정보 반환", notes = "Token은 매 실행 시 갱신된다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 User의 JSON이 반환되었음"),
    })
    public ResponseEntity<UserRes> getTokenAndJoinOrLogin(@ApiParam(value="Token 생성에 사용될 Code", required = true) @RequestParam("code") String code) {
        /**
         * @Method Name : getTokenAndJoinOrLogin
         * @작성자 : 김민권
         * @Method 설명 : 카카오톡 로그인 이후 Token을 얻어온 후, 회원가입 또는 로그인을 처리하는 Method
         */

        // 1. Token을 발급받는다.
        HashMap<String, String> kakaoTokens = authService.getKakaoTokens(code);

        // 2. Token 값을 통해 UserInfo를 받아온다.
        KakaoUserRes kakaoUserRes = authService.getKakaoUserInfo(kakaoTokens.get("access_token"));

        // 3. UserInfo의 내용이 회원 DB에 존재하는가?
        String accountEmailOrId = kakaoUserRes.getKakao_account().getEmail();
        if(accountEmailOrId == null || accountEmailOrId == "") {
            accountEmailOrId = String.valueOf(kakaoUserRes.getId()) + EMAIL_SUFFIX;
        }
        UserRes userResForCheck = userService.findUserByAccountEmail(accountEmailOrId);

        UserRes userRes;
        if(userResForCheck != null) {
            // 존재한다면 Token 값을 갱신하고 반환한다.
            userRes = authService.refreshTokensForExistUser(userResForCheck.getAccountEmail(), kakaoTokens.get("access_token"), kakaoTokens.get("refresh_token"));
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            userRes = userService.insertUser(kakaoUserRes.toUser(kakaoTokens.get("access_token"), kakaoTokens.get("refresh_token")));
        }

        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @GetMapping("/kakao/logout")
    @ApiOperation(value = "kakao에 로그아웃을 위한 신호를 보낸다.", notes = "현재 로그인되어 있는 유저의 2가지 Token 유효성을 만료시킨다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃되었음"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "전송된 이메일로 유저정보를 찾을 수 없음"),
    })
    public ResponseEntity<String> logout(@ApiIgnore @RequestHeader("Authorization") String accessToken) {
        /**
         * @Method Name : logout
         * @작성자 : 김민권
         * @Method 설명 : Kakao API에 로그아웃이 되었다는 신호를 전송한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            authService.logout(userRes);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/kakao/update/{accountEmail}")
    @ApiOperation(value = "Access Token을 재발급한다.", notes = "재발급 된 Token을 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적인 URL 전송 완료"),
            @ApiResponse(code = 400, message = "전송된 이메일로 유저정보를 찾을 수 없음"),
    })
    public ResponseEntity<String> accessTokenUpdate(@ApiParam(value="Token을 재발급할 User email", required = true) @PathVariable("accountEmail") String accountEmail) {
        /**
         * @Method Name : accessTokenUpdate
         * @작성자 : 김민권
         * @Method 설명 : AccessToken이 만료되었음을 확인 시, refresh token을 통해 재발급을 요청하는 Method
         */
        String newToken = authService.accessTokenUpdate(accountEmail);
        if(newToken == null) {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

    @PatchMapping("/user")
    @ApiOperation(value = "User를 업데이트한다.", notes = "Token의 유효성을 확인 후, User의 정보를 업데이트한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적인 수정 완료"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "전송된 이메일로 유저정보를 찾을 수 없음"),
    })
    public ResponseEntity<UserRes> updateUser(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestBody @ApiParam(value="수정할 정보", required = true) UserUpdatePatchReq userUpdatePatchReq) {
        /**
         * @Method Name : updateUser
         * @작성자 : 김민권
         * @Method 설명 : User에 대한 정보를 업데이트한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            UserRes userRes = userService.updateUser(userUpdatePatchReq);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    @ApiOperation(value = "내 정보를 반환한다.", notes = "Token의 유효성을 확인 후, 내 정보를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적인 수정 완료"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "서버에 오류가 있음"),
    })
    public ResponseEntity<UserRes> getMyInfo(@ApiIgnore @RequestHeader("Authorization") String accessToken) {
        /**
         * @Method Name : getMyInfo
         * @작성자 : 김민권
         * @Method 설명 : 나에 대한 정보를 반환한다.
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);

        if(validTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if(validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{accountEmail}")
    @ApiOperation(value = "다른 사람의 정보를 반환한다.", notes = "Token의 유효성을 확인 후, 다른 사람의 정보를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적인 수정 완료"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "서버에 오류가 있음"),
    })
    public ResponseEntity<UserRes> getOtherUserInfo(@ApiIgnore @RequestHeader("Authorization") String accessToken,
                                               @PathVariable("accountEmail") String accountEmail) {
        /**
         * @Method Name : getUserInfo
         * @작성자 : 김민권
         * @Method 설명 : 다른 사람의 정보를 반환한다.
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);

        if(validTokenStatusValue == 200) {
            UserRes userRes = userService.findUserByAccountEmail(accountEmail);
            return new ResponseEntity<>(userRes, HttpStatus.OK);
        } else if(validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
