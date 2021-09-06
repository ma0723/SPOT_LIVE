package com.ssafy.spotlive.api.request.video;

import com.ssafy.spotlive.db.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : VideoInsertPostReq
 * @작성자 : 권영린
 * @Class 설명 : 스트리밍 시작 API 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("VideoInsertPostReq")
public class VideoInsertPostReq {
    @ApiModelProperty(name="videoTitle : 라이브 방송 타이틀", example="옥탑방 고냥쓰 홍보합니다")
    String videoTitle;
    @ApiModelProperty(name="videoDescription : 라이브 방송 설명", example="전세계를 강타한 화재의 연극 옥탑방 고냥쓰")
    String videoDescription;
    @ApiModelProperty(name="mode : 라이브 모드 설정", example="소통")
    String mode;
    @ApiModelProperty(name="categoryId : 카테고리 ID", example="1")
    Long categoryId;
    @ApiModelProperty(name="showInfoId : 공연의 경우 해당 공연ID, 홍보의 경우 연결된 공연ID, 홍보의 경우 비움, 없는 공연 Id를 넣으면 500 에러", example="")
    Long showInfoId;
    @ApiModelProperty(name="timetableId : 위 공연의 타임테이블 아이디", example="")
    Long timetableId;
    @ApiModelProperty(name="accountEmail : 유저 email", example="email@email.com", hidden = true)
    String accountEmail;
    @ApiModelProperty(name="sessionId : Openvidu Server의 Session id", example="session12345678")
    String sessionId;

    public Video toVideo(String thumbnailUrl){
        /**
         * @Method Name : toVideo
         * @작성자 : 권영린
         * @Method 설명 : VideoInsertPostReqDto를 Entity로 변환하는 메소드
         */
        Video video = new Video();

        ShowInfo showInfo = new ShowInfo();
        User user = new User();
        System.out.println(showInfoId);

        video.setVideoTitle(this.videoTitle);
        video.setVideoDescription(this.videoDescription);
        video.setMode(this.mode);
        video.setThumbnailUrl(thumbnailUrl);
        video.setIsLive(Boolean.TRUE);
        video.setHit(0L);

        Category category = new Category();
        category.setCategoryId(this.categoryId);
        video.setCategory(category);

        if(this.showInfoId != null) {
            showInfo.setShowInfoId(this.showInfoId);
            video.setShowInfo(showInfo);
        }

        if(this.timetableId != null) {
            Timetable timetable = new Timetable();
            timetable.setTimetableId(this.timetableId);
            video.setTimetable(timetable);
        }

        user.setAccountEmail(this.accountEmail);
        video.setUser(user);
        video.setSessionId(this.sessionId);

        return video;
    }

}
