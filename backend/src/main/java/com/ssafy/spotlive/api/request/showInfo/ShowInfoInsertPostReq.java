package com.ssafy.spotlive.api.request.showInfo;

import com.ssafy.spotlive.api.request.timetable.TimetableInsertPostReq;
import com.ssafy.spotlive.db.entity.ShowInfo;
import com.ssafy.spotlive.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @FileName : ShowInfoInsertPostReq
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보 등록 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
@ApiModel("ShowInfoInsertPostReq")
public class ShowInfoInsertPostReq {
    @ApiModelProperty(name = "accountEmail", hidden = true)
    String accountEmail;
    @ApiModelProperty(name = "showInfoTitle", example = "공연 제목")
    String showInfoTitle;
    @ApiModelProperty(name = "posterUrl", hidden = true)
    String posterUrl;
    @ApiModelProperty(name = "showInfoDescription", example = "공연에 대한 설명")
    String showInfoDescription;
    @ApiModelProperty(name = "price", example = "20000")
    Long price;
    @ApiModelProperty(name = "runningTime", example = "180")
    int runningTime;

    @ApiModelProperty(name = "timetableList")
    List<TimetableInsertPostReq> timetableInsertPostReq;

    public ShowInfo toShowInfo(){
        /**
         * @Method Name : toShowInfo
         * @작성자 : 금아현
         * @Method 설명 : ShowInfo DTO를 Entity로 변환
         */
        ShowInfo showInfo = new ShowInfo();
        User user = new User();

        user.setAccountEmail(this.accountEmail);
        showInfo.setUser(user);
        showInfo.setShowInfoTitle(this.showInfoTitle);
        showInfo.setPosterUrl(this.posterUrl);
        showInfo.setShowInfoDescription(this.showInfoDescription);
        showInfo.setPrice(this.price);
        showInfo.setRunningTime(this.runningTime);

        return showInfo;
    }

}
