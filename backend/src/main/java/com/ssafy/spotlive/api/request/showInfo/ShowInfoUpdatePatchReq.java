package com.ssafy.spotlive.api.request.showInfo;

import com.ssafy.spotlive.api.request.timetable.TimetableInsertPostReq;
import com.ssafy.spotlive.db.entity.ShowInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @FileName : ShowInfoUpdatePatchReq
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보 수정 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
@ApiModel("ShowInfoUpdatePatchReq")
public class ShowInfoUpdatePatchReq {
    @ApiModelProperty(name = "showInfoTitle", example = "수정할 제목입니다.")
    String showInfoTitle;
    @ApiModelProperty(name = "showInfoDescription", example = "수정할 설명입니다.")
    String showInfoDescription;
    @ApiModelProperty(name = "posterUrl", hidden = true)
    String posterUrl;
    @ApiModelProperty(name = "price", example = "20000")
    Long price;
    @ApiModelProperty(name = "runningTime", example = "180")
    int runningTime;

    @ApiModelProperty(name = "timetableList")
    List<TimetableInsertPostReq> timetableInsertPostReq;

    public ShowInfo toShowInfo() {
        /**
         * @Method Name : toShowInfo
         * @작성자 : 금아현
         * @Method 설명 : ShowInfo DTO를 Entity로 변환
         */
        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowInfoTitle(this.showInfoTitle);
        showInfo.setShowInfoDescription(this.showInfoDescription);
        showInfo.setPosterUrl(this.posterUrl);
        showInfo.setPrice(this.price);
        showInfo.setRunningTime(this.runningTime);
        return showInfo;
    }
}
