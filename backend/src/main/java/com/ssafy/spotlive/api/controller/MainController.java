package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.response.main.UserFindFollowGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindAllGetRes;
import com.ssafy.spotlive.api.response.main.VideoGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindMainVideoRes;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.MainService;
import com.ssafy.spotlive.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @FileName : MainController
 * @작성자 : 강용수
 * @Class 설명 : 메인화면 데이터 조회를 수행하는 컨트롤러
 */
@RestController
@RequestMapping("/api/main")
@CrossOrigin(value = {"*"}, maxAge = 6000)
@Api(value = "MainController", tags = "MainController", description = "메인화면 컨트롤러")
public class MainController {
    @Autowired
    MainService mainService;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "메인화면 진입 시 모든 Video 조회", notes = "메인화면 진입 시 모든 Video를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/all")
    public ResponseEntity<VideoFindAllGetRes> findAllVideo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllVideo
         * @작성자 : 강용수
         * @Method 설명 : 메인 화면 진입 시 모든 Video들을 조회하는 메소드
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);

        if (validTokenStatusValue == 200) {
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);

            VideoFindAllGetRes videoFindAllGetRes = mainService.findAllVideo(page, size, categoryId, userRes.getAccountEmail());

            if (videoFindAllGetRes == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(videoFindAllGetRes, HttpStatus.OK);
        } else if (validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "홍보 Video 조회", notes = "mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 검색된 홍보 Video를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/ad")
    public ResponseEntity<VideoGetRes> findAllAdVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllAdVideo
         * @작성자 : 강용수
         * @Method 설명 : mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 홍보 Video를 검색하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllVideoByModeAndCategoryId(page, size, categoryId, "홍보");

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "소통 Video 조회", notes = "mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 검색된 소통 Video를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/talk")
    public ResponseEntity<VideoGetRes> findAllTalkVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllTalkVideo
         * @작성자 : 강용수
         * @Method 설명 : mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 소통 Video를 검색하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllVideoByModeAndCategoryId(page, size, categoryId, "소통");

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "공연 Video 조회", notes = "mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 검색된 공연 Video를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/show")
    public ResponseEntity<VideoGetRes> findAllShowVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllShowVideo
         * @작성자 : 강용수
         * @Method 설명 : mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 공연 Video를 검색하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllVideoByModeAndCategoryId(page, size, categoryId, "공연");

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "조회수 순 다시보기 Video 조회", notes = "다시보기 Video를 카테고리 id 기준과 조회수 순으로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/replay")
    public ResponseEntity<VideoGetRes> findAllReplayVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllReplayVideo
         * @작성자 : 강용수
         * @Method 설명 : 다시보기 Video를 카테고리 id 기준과 조회수 순으로 조회하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllReplayVideoByIsLiveAndCategoryId(page, size, categoryId);

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "시청자 순 라이브 Video 조회", notes = "라이브 Video를 카테고리 id 기준과 시청자수 순으로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/live")
    public ResponseEntity<VideoGetRes> findAllLiveVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllLiveVideo
         * @작성자 : 강용수
         * @Method 설명 : 라이브 Video를 카테고리 id 기준과 시청자수 순으로 조회하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllLiveVideoByIsLiveAndCategoryId(page, size, categoryId);

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "자신이 팔로우 한 유저의 Video 조회", notes = "자신이 팔로우 한 유저의 Video를 카테고리 id 기준으로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/follow")
    public ResponseEntity<VideoGetRes> findAllFollowVideo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(name = "categoryId", required = false) Long categoryId){
        /**
         * @Method Name : findAllFollowVideo
         * @작성자 : 강용수
         * @Method 설명 : 자신이 팔로우한 유저의 Video를 카테고리 id 기준으로 조회하는 메소드
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);

        if (validTokenStatusValue == 200) {
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);

            VideoGetRes videoGetRes = mainService.findAllFollowVideoByCategoryId(page, size, categoryId, userRes.getAccountEmail());

            List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

            if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
        } else if (validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "자신이 팔로우 한 유저 리스트 조회", notes = "자신이 팔로우 한 유저 리스트를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/user")
    public ResponseEntity<List<UserFindFollowGetRes>> findAllFollowByFan(@ApiIgnore @RequestHeader("Authorization") String accessToken){
        /**
         * @Method Name : findAllFollowByFan
         * @작성자 : 강용수
         * @Method 설명 : 자신이 팔로우한 유저 리스트를 조회하는 메소드
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);
        
        if (validTokenStatusValue == 200) {
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);

            List<UserFindFollowGetRes> userFindFollowGetResList = mainService.findAllFollowByFan(userRes.getAccountEmail());

            if (userFindFollowGetResList == null || userFindFollowGetResList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(userFindFollowGetResList, HttpStatus.OK);
        } else if (validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "상단 캐루젤에 표시될 영상 조회", notes = "메인화면 상단 캐루젤에 표시될 영상을 조회하는 메소드")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/top")
    public ResponseEntity<List<VideoFindMainVideoRes>> findAllTopVideo(@ApiIgnore @RequestHeader("Authorization") String accessToken){
        /**
         * @Method Name : findAllTopVideo
         * @작성자 : 강용수
         * @Method 설명 : 상단 캐루젤에 표시될 영상을 조회하는 메소드
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);

        if (validTokenStatusValue == 200) {
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);

            List<VideoFindMainVideoRes> videoFindMainVideoResList = mainService.findAllTopVideo(userRes.getAccountEmail());

            if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(videoFindMainVideoResList, HttpStatus.OK);
        } else if (validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "입력된 keyword가 포함되는 제목과 설명이 있는 Video를 categoryId 기준으로 조회", notes = "입력된 keyword가 포함되는 제목과 설명이 있는 Video를 categoryId 기준으로 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/search")
    public ResponseEntity<VideoGetRes> findAllSearchVideo(
            @RequestParam("size") int size, @RequestParam("page") int page, @RequestParam("keyword") String keyword){
        /**
         * @Method Name : findAllSearchVideo
         * @작성자 : 강용수
         * @Method 설명 : 입력된 keyword가 포함되는 제목과 설명이 있는 Video를 categoryId 기준으로 조회하는 메소드
         */
        VideoGetRes videoGetRes = mainService.findAllSearchVideoByKeywordContains(page, size, keyword);

        List<VideoFindMainVideoRes> videoFindMainVideoResList = videoGetRes.getVideoResList();

        if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(videoGetRes, HttpStatus.OK);
    }

    @ApiOperation(value = "본인이 예약한 라이브 공연의 videoList 조회", notes = "본인이 예약한 라이브 공연의 videoList를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/reservation")
    public ResponseEntity<List<VideoFindMainVideoRes>> findAllReservationVideo(@ApiIgnore @RequestHeader("Authorization") String accessToken){
        /**
         * @Method Name : findAllReservationVideo
         * @작성자 : 강용수
         * @Method 설명 : 본인이 예약한 라이브 공연의 videoList를 조회하는 메소드
         */

        int validTokenStatusValue = authService.isValidToken(accessToken);

        if (validTokenStatusValue == 200) {
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);

            List<VideoFindMainVideoRes> videoFindMainVideoResList = mainService.findAllReservationVideoByModeAndTimetableIdIn("공연", userRes.getAccountEmail());

            if (videoFindMainVideoResList == null || videoFindMainVideoResList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(videoFindMainVideoResList, HttpStatus.OK);
        } else if (validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}