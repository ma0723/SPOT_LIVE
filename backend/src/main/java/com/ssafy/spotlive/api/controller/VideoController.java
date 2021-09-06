package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.request.video.VideoInsertPostReq;
import com.ssafy.spotlive.api.request.video.VideoInsertUrlByIdPostReq;
import com.ssafy.spotlive.api.request.video.VideoUpdateByIdPatchReq;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.response.video.VideoFindAllByUserIdGetRes;
import com.ssafy.spotlive.api.response.video.VideoFindByIdGetRes;
import com.ssafy.spotlive.api.response.video.VideoInsertPostRes;
import com.ssafy.spotlive.api.response.video.VideoOpenViduSessionGetRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.UserService;
import com.ssafy.spotlive.api.service.UserVideoService;
import com.ssafy.spotlive.api.service.VideoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @FileName : VideoController
 * @작성자 : 권영린
 * @Class 설명 : 영상에 대한 CRUD를 수행하는 컨트롤러
 */
@Api(value = "Video API", tags = {"VideoController"}, description = "영상 컨트롤러")
@RestController
@CrossOrigin(value = {"*"}, maxAge = 6000)
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    UserVideoService userVideoService;

    @PostMapping("/insert")
    @ApiOperation(value = "스트리밍 시작", notes = "스트리밍을 시작하면 영상정보가 DB에 저장된다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "스트리밍 시작 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<VideoInsertPostRes> insertVideo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestPart(value = "posterImage", required = false) MultipartFile thumbnailImage,
            @RequestPart(value = "videoInsertPostReq") VideoInsertPostReq videoInsertPostReq) {
        /**
         * @Method Name : insertVideo
         * @작성자 : 권영린
         * @Method 설명 : 비디오를 추가한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            videoInsertPostReq.setAccountEmail(userRes.getAccountEmail());
            VideoInsertPostRes videoInsertPostRes = videoService.insertVideo(videoInsertPostReq, thumbnailImage);
            userVideoService.joinUserVideo(userRes.getAccountEmail(), videoInsertPostRes.getVideoId());
            return new ResponseEntity<>(videoInsertPostRes, HttpStatus.CREATED);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/openvidu/session")
    @ApiOperation(value = "Openvidu를 위한 세션을 생성", notes = "openvidu를 위한 세션을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "서버 오류 발생"),
    })
    public ResponseEntity<String> createSessionIdForOpenVidu(
            @ApiIgnore @RequestHeader("Authorization") String accessToken) {
        /**
         * @Method Name : createSessionAndMakeTokenForOpenVidu
         * @작성자 : 김민권
         * @Method 설명 : openvidu를 위한 세센을 생성한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String sessionId = videoService.createSession();
            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/openvidu/token/{sessionId}")
    @ApiOperation(value = "Openvidu를 위한 토큰을 생성", notes = "openvidu를 위한 토큰을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 400, message = "올바르지 않은 접근"),
            @ApiResponse(code = 401, message = "올바르지 않은 Token이거나, 만료된 Token, 재발급 요청이 필요"),
            @ApiResponse(code = 500, message = "서버 오류 발생"),
    })
    public ResponseEntity<VideoOpenViduSessionGetRes> createTokenForOpenVidu(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable("sessionId") String sessionId) {
        /**
         * @Method Name : createSessionAndMakeTokenForOpenVidu
         * @작성자 : 김민권
         * @Method 설명 : openvidu를 위한 토큰을 생성한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            VideoOpenViduSessionGetRes videoOpenViduSessionGetRes = videoService.createToken(sessionId);
            return new ResponseEntity<>(videoOpenViduSessionGetRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{videoId}")
    @ApiOperation(value = "영상 정보 조회", notes = "videoId로 조회를 하면 해당 영상에 대한 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<VideoFindByIdGetRes> findVideoById(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable long videoId) {
        /**
         * @Method Name : findVideoById
         * @작성자 : 권영린
         * @Method 설명 : 비디오 정보를 조회한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            VideoFindByIdGetRes videoFindByIdGetRes = videoService.findVideoById(videoId);
            return new ResponseEntity<>(videoFindByIdGetRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{videoId}")
    @ApiOperation(value = "스트리밍 정보 수정", notes = "videoId와 수정정보를 요청받아 해당 영상에 대한 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "수정 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateVideoById(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable long videoId,
            @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
            @RequestPart(value = "videoUpdateByIdPatchReq", required = false) VideoUpdateByIdPatchReq videoUpdateByIdPatchReq) {
        /**
         * @Method Name : updateVideoById
         * @작성자 : 권영린
         * @Method 설명 : 비디오를 수정한다.
         */
        System.out.println("===================================================================");
        System.out.println("* videoUpdateByIdPatchReq");
        System.out.println(videoUpdateByIdPatchReq);
        System.out.println("* accessToken");
        System.out.println(accessToken);
        System.out.println("* thumbnailImage");
        System.out.println(thumbnailImage);
        System.out.println("===================================================================");

        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            Boolean isSuccess = videoService.updateVideoById(videoId, thumbnailImage, videoUpdateByIdPatchReq, userRes.getAccountEmail());
            if(isSuccess)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<String>("없는 영상이거나 남의 영상이거나 아무튼 실패함", HttpStatus.BAD_REQUEST);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{videoId}")
    @ApiOperation(value = "스트리밍 정보 삭제", notes = "Video 다시보기를 저장하지 않는경우 비디오에 대한 데이터를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "수정 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> deleteVideoById(
            @ApiIgnore @RequestHeader("Authorization") String accessToken, @PathVariable long videoId) {
        /**
         * @Method Name : deleteVideoById
         * @작성자 : 김민권
         * @Method 설명 : 비디오를 삭제한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            videoService.deleteVideoById(videoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/close/{videoId}")
    @ApiOperation(value = "스트리밍 종료", notes = "videoId로 해당 스트리밍의 end_time을 DB에 기록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "종료 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> closeVideoById(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable long videoId) {
        /**
         * @Method Name : closeVideoById
         * @작성자 : 권영린
         * @Method 설명 : 비디오를 종료한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            if(videoService.updateVideoEndTimeById(videoId, userRes.getAccountEmail()))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<String>("이미 종료됐거나 남의 영상이거나 아무튼 실패함.", HttpStatus.BAD_REQUEST);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/record")
    @ApiOperation(value = "비디오 URL 추가", notes = "videoId에 해당하는 DB에 RecordURL을 기록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "종료 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<VideoFindByIdGetRes> insertRecordUrlById(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestBody @ApiParam(value="삽입할 URL 정보", required = true) VideoInsertUrlByIdPostReq videoInsertUrlByIdPostReq) {
        /**
         * @Method Name : insertRecordUrlById
         * @작성자 : 김민권
         * @Method 설명 : 비디오를 URL을 저장한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            VideoFindByIdGetRes videoFindByIdGetRes = videoService.insertRecordUrlById(videoInsertUrlByIdPostReq);
            return new ResponseEntity<>(videoFindByIdGetRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{accountEmail}")
    @ApiOperation(value = "특정 유저 영상 리스트 조회", notes = "user email를 요청받아 해당 유저의 저장된 영상리스트를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<VideoFindAllByUserIdGetRes>> findVideoListByUserId(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable String accountEmail) {
            /**
             * @Method Name : findVideoListByUserId
             * @작성자 : 권영린
             * @Method 설명 : 특정 유저의 내동영상 리스트를 조회한다.
             */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            List<VideoFindAllByUserIdGetRes> videoFindAllByUserIdGetRes = videoService.findVideoByAccountEmail(accountEmail);
            return new ResponseEntity<>(videoFindAllByUserIdGetRes, HttpStatus.OK);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/join/{videoId}")
    @ApiOperation(value = "조회수 1 증가", notes = "비디오 아이디를 입력받아 조회수를 1 증가한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "조회수 증가 성공"),
            @ApiResponse(code = 204, message = "비디오Id로 해당 비디오 조회 실패"),
            @ApiResponse(code = 400, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateVideoHitPlusById(
            @PathVariable Long videoId) {
        /**
         * @Method Name : updateVideoHitPlusById
         * @작성자 : 권영린
         * @Method 설명 : 비디오Id에 해당하는 비디오데이터의 조회수(hit)을 1 증가시킨다.
         */
        if(videoService.updateVideoHitPlusById(videoId))
            return new ResponseEntity<String>("조회수 증가 성공", HttpStatus.CREATED);
        else
            return new ResponseEntity<String>("없는 아이디 조회로 실패", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/exit/{videoId}")
    @ApiOperation(value = "조회수 1 감소", notes = "비디오 아이디를 입력받아 조회수를 1 감소시킨다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "조회수 감소 성공"),
            @ApiResponse(code = 204, message = "비디오Id로 해당 비디오 조회 실패"),
            @ApiResponse(code = 400, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateVideoHitMinusById(
            @PathVariable Long videoId) {
        /**
         * @Method Name : updateVideoHitMinusById
         * @작성자 : 권영린
         * @Method 설명 : 비디오Id에 해당하는 비디오데이터의 조회수(hit)을 1 감소시킨다.
         */
        if(videoService.updateVideoHitMinusById(videoId))
            return new ResponseEntity<String>("조회수 증가 성공", HttpStatus.CREATED);
        else
            return new ResponseEntity<String>("없는 아이디 조회로 실패", HttpStatus.NO_CONTENT);
    }
}
