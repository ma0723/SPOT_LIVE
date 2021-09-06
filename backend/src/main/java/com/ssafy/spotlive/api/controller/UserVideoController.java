package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.response.uservideo.UserVideoRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.UserService;
import com.ssafy.spotlive.api.service.UserVideoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @FileName : UserVideoController
 * @작성자 : 김민권
 * @Class 설명 : User의 Video 참여, 나가기 관련 기능을 수행하는 컨트롤러
 */
@RestController
@RequestMapping("/api/uservideo")
@CrossOrigin(value = {"*"}, maxAge = 6000)
@Api(value = "UserVideoController", tags = "UserVideoController", description = "유저 비디오 컨트롤러")
public class UserVideoController {

    @Autowired
    UserVideoService userVideoService;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "비디오에 참여한다.", notes = "현재 로그인한 유저가 특정 비디오로 접속한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "팔로우 성공"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "전송된 이메일로 유저정보를 찾을 수 없음"),
    })
    @PostMapping("/{videoId}")
    public ResponseEntity<UserVideoRes> joinUserVideo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @ApiParam(value="Join할 Video의 PK인 VideoId", required = true) @PathVariable("videoId") long videoId) {
        /**
         * @Method Name : joinUserVideo
         * @작성자 : 김민권
         * @Method 설명 : 현재 로그인한 유저가 특정 비디오로 접속한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            UserVideoRes userVideoRes = userVideoService.joinUserVideo(userRes.getAccountEmail(), videoId);
            return new ResponseEntity<>(userVideoRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "비디오에서 나간다.", notes = "현재 로그인한 유저가 접속한 비디오에서 나간다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "팔로우 성공"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "전송된 이메일로 유저정보를 찾을 수 없음"),
    })
    @DeleteMapping("/{videoId}")
    public ResponseEntity<String> leaveUserVideo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @ApiParam(value="나갈 Video의 PK인 VideoId", required = true) @PathVariable("videoId") long videoId) {
        /**
         * @Method Name : leaveUserVideo
         * @작성자 : 김민권
         * @Method 설명 : 현재 로그인한 유저가 접속한 비디오에서 나간다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            userVideoService.leaveUserVideo(userRes.getAccountEmail(), videoId);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
