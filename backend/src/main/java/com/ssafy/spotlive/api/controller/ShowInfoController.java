package com.ssafy.spotlive.api.controller;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.showInfo.ShowInfoUpdatePatchReq;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoFindByIdGetRes;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import com.ssafy.spotlive.api.response.timetable.TimetableRes;
import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.api.service.AuthService;
import com.ssafy.spotlive.api.service.ShowInfoService;
import com.ssafy.spotlive.api.service.TimetableService;
import com.ssafy.spotlive.api.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @FileName : ShowInfoController
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보(ShowInfo)에 대한 CRUD를 수행하는 컨트롤러
 */
@Api(value = "공연 정보 API", tags = {"ShowInfoController"}, description = "공연 정보 컨트롤러")
@RestController
@CrossOrigin(value = {"*"}, maxAge = 6000)
@RequestMapping("/api/showinfo")
public class ShowInfoController {

    @Autowired
    ShowInfoService showInfoService;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    TimetableService timetableService;

    @PostMapping(
            value="/",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(value = "공연 정보 등록", notes = "새로운 공연 정보를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "등록 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Object> insertShowInfo(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @RequestPart(value = "posterImage", required = false) MultipartFile posterImage,
            @RequestPart(value = "showInfoInsertPostReq") ShowInfoInsertPostReq showInfoInsertPostReq
    ){
        /**
         * @Method Name : insertShowInfo
         * @작성자 : 금아현
         * @Method 설명 : 새로운 공연 정보를 등록한다.
         */
        System.out.println("===========================================================================================================");
        System.out.println("* DATA: " +  showInfoInsertPostReq);
        if(showInfoInsertPostReq.getTimetableInsertPostReq() == null) {
            System.out.println("!!!!!!!!!!========== TIME TABLE이 NULL입니다 ==========!!!!!!!!!!");
        } else {
            System.out.println("* SIZE: " +  showInfoInsertPostReq.getTimetableInsertPostReq().size());
            System.out.println("* TIME_TABLE: " +  showInfoInsertPostReq.getTimetableInsertPostReq());
        }
        System.out.println("===========================================================================================================");

        int validTokenStatusValue = authService.isValidToken(accessToken);
        if(validTokenStatusValue == 200){
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);
            showInfoInsertPostReq.setAccountEmail(userRes.getAccountEmail());
            showInfoService.insertShowInfo(showInfoInsertPostReq, posterImage);
        }else if(validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "공연 정보 단일 조회", notes = "공연 id로 해당 공연을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ShowInfoFindByIdGetRes> findShowInfoById(
            @PathVariable @ApiParam(value = "조회할 공연 정보의 id", required = true) long id){
        /**
         * @Method Name : findShowInfoById
         * @작성자 : 금아현
         * @Method 설명 : 공연 id로 공연 정보를 조회한다.
         */
        ShowInfoFindByIdGetRes showInfoFindByIdGetRes = showInfoService.findShowInfoById(id);
        if(showInfoFindByIdGetRes == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(showInfoFindByIdGetRes, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "공연 정보 수정", notes = "공연 id로 해당 공연을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "해당하는 id의 공연정보가 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Object> updateShowInfoById(
            @PathVariable @ApiParam(value = "수정할 공연 정보의 id", required = true) long id,
            @RequestPart(value = "posterImage", required = false) MultipartFile posterImage,
            @RequestPart(value = "showInfoUpdatePatchReq") ShowInfoUpdatePatchReq showInfoUpdatePatchReq){
        /**
         * @Method Name : updateShowInfoById
         * @작성자 : 금아현
         * @Method 설명 : 공연 id로 해당 공연을 수정한다.
         */
        Boolean isSuccess = showInfoService.updateShowInfoById(id, showInfoUpdatePatchReq, posterImage);
        if(isSuccess) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "공연 정보 삭제", notes = "공연 id로 해당 공연을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "해당하는 id의 공연정보가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<Object> deleteShowInfoById(
            @PathVariable @ApiParam(value = "삭제할 공연 정보의 id", required = true) long id){
        /**
         * @Method Name : deleteShowInfo
         * @작성자 : 금아현
         * @Method 설명 : 공연 id로 해당 공연을 삭제한다.
         */
        Long exist = showInfoService.deleteShowInfoById(id);
        if(exist == 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timetable/{id}")
    @ApiOperation(value = "가장 가까운 공연 시간 조회", notes = "공연 id로 현재 시간의 30분 전후의 공연을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<TimetableRes> findTimetableByShowInfoId(
            @PathVariable @ApiParam(value = "조회할 공연 정보의 id", required = true) long id){
        /**
         * @Method Name : findTimetableByShowInfoId
         * @작성자 : 금아현
         * @Method 설명 : 공연 id로 가장 가까운 공연 시간 1개를 조회한다.
         */
        TimetableRes timetableRes = timetableService.findTimetableByShowInfoId(id);
        if(timetableRes == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(timetableRes, HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "해당 유저의 공연 조회", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<List<ShowInfoRes>> findShowInfoByUser(
            @ApiIgnore @RequestHeader("Authorization") String accessToken){
        /**
         * @Method Name : findShowInfoByUser
         * @작성자 : 금아현
         * @Method 설명 : user정보로 공연을 조회한다.
         */
        int validTokenStatusValue = authService.isValidToken(accessToken);
        if(validTokenStatusValue == 200){
            String[] splitToken = accessToken.split(" ");
            UserRes userRes = userService.findUserByAccessToken(splitToken[1]);
            List<ShowInfoRes> showInfoRes = showInfoService.findShowInfoByUser(userRes.getAccountEmail());
            if(showInfoRes == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(showInfoRes, HttpStatus.OK);
        }else if(validTokenStatusValue == 401) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
