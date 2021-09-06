package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.response.follow.FollowFindByArtistAccountEmailGetRes;
import com.ssafy.spotlive.api.response.follow.FollowFindByFanAccountEmailGetRes;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.FollowService;
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
 * @FileName : FollowController
 * @작성자 : 권영린
 * @Class 설명 : 팔로우 관련 기능을 수행하는 컨트롤러
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(value = {"*"}, maxAge = 6000)
@Api(value = "FollowController", tags = "FollowController", description = "팔로우 컨트롤러")
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @ApiOperation(value = "팔로우", notes = "팔로우 버튼 클릭시 해당유저를 팔로우하는 기능 수행 | 임시로 토큰 말고 그냥 자기 이메일 입력으로 만들어놓음")
    @ApiResponses({
            @ApiResponse(code = 200, message = "팔로우 성공"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @PostMapping("/follow/{artistEmail}")
    public ResponseEntity<?> followArtist(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable String artistEmail){
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String[] spitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(spitToken[1]);
            followService.insertFollowByAccountEmail(artistEmail, userRes.getAccountEmail());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "언팔로우", notes = "언팔로우 버튼 클릭시 해당유저를 언팔로우하는 기능 수행 | 임시로 토큰 말고 그냥 자기 이메일 입력으로 만들어놓음")
    @ApiResponses({
            @ApiResponse(code = 204, message = "언팔로우 성공"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @DeleteMapping("/unfollow/{artistEmail}")
    public ResponseEntity<?> unfollowArtist(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable String artistEmail){
        /**
         * @Method Name : unfollowArtist
         * @작성자 : 권영린
         * @Method 설명 : 이미 팔로우 되어있는 아티스트의 경우 언팔로우 버튼이 활성화 되어있는데, 그 버튼 클릭
         *                하면 팔로우 테이블에서 삭제를 한다.
         */
        int vaildTokenStatusValue = authService.isValidToken(accessToken);

        if(vaildTokenStatusValue == 200) {
            String spitToken = accessToken.split(" ")[1];
            followService.deleteFollowByAccountEmail(spitToken, artistEmail);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if(vaildTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "팬이 팔로우하는 아티스트 리스트", notes = "토큰 있으면 내정보로, 없으면 쿼리스트링으로 팬이메일 전달")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/artist/list")
    public ResponseEntity<?> findArtistByFan(
            @RequestParam(required = false) String fanEmail,
            @ApiIgnore @RequestHeader(value = "Authorization", required = false) String accessToken) {
        /**
         * @Method Name : findArtistByFan
         * @작성자 : 권영린
         * @Method 설명 : 토큰 있으면 내정보로, 없으면 쿼리스트링으로 팬이메일 전달받아 팔로우중인 아티스트 목록을 반환
         */
        List<FollowFindByFanAccountEmailGetRes> followFindByFanAccountEmailGetResList = null;
        if(fanEmail != null) {
            followFindByFanAccountEmailGetResList = followService.findArtistByFanAccountEmail(fanEmail);
        } else if (accessToken != null){
            UserRes userByAccessToken = userService.findUserByAccessToken(accessToken);
            String myEmail = userByAccessToken.getAccountEmail();
            followFindByFanAccountEmailGetResList = followService.findArtistByFanAccountEmail(myEmail);
        } else {
            return new ResponseEntity<>("토큰과 이메일 둘 다 없습니다.;;", HttpStatus.BAD_REQUEST);
        }
        if (followFindByFanAccountEmailGetResList.size() == 0)
            return new ResponseEntity<>("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(followFindByFanAccountEmailGetResList, HttpStatus.OK);
    }

    @ApiOperation(value = "아티스트를 팔로우하는 팬 리스트", notes = "토큰 있으면 내정보로, 없으면 쿼리스트링으로 아티스트이메일 전달")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/fan/list")
    public ResponseEntity<?> findFanByArtist(
            @RequestParam(required = false) String artistEmail,
            @ApiIgnore @RequestHeader(value = "Authorization", required = false) String accessToken
    ){
        /**
         * @Method Name : findFanByArtist
         * @작성자 : 권영린
         * @Method 설명 : 토큰 있으면 내정보로, 없으면 쿼리스트링으로 아티스트이메일 전달받아 해당 유저를 팔로우중인 팬 목록을 반환
         */
        List<FollowFindByArtistAccountEmailGetRes> followFindByArtistAccountEmailGetResList = null;
        if (artistEmail != null) {
            followFindByArtistAccountEmailGetResList = followService.findFanByArtistAccountEmail(artistEmail);
        } else if (accessToken != null){
            UserRes userByAccessToken = userService.findUserByAccessToken(accessToken);
            String myEmail = userByAccessToken.getAccountEmail();
            followFindByArtistAccountEmailGetResList = followService.findFanByArtistAccountEmail(myEmail);
        } else {
            return new ResponseEntity<>("토큰과 이메일 둘 다 없습니다.;;", HttpStatus.BAD_REQUEST);
        }
        if(followFindByArtistAccountEmailGetResList.size() == 0)
            return new ResponseEntity<>("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(followFindByArtistAccountEmailGetResList, HttpStatus.OK);
    }

}
