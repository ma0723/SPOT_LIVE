package com.ssafy.spotlive.api.response.showInfo;

import com.ssafy.spotlive.api.response.user.UserRes;
import com.ssafy.spotlive.db.entity.ShowInfo;
import lombok.Builder;
import lombok.Data;

/**
 * @FileName : ShowInfoRes
 * @작성자 : 금아현
 * @Class 설명 : 공연정보 대한 ResponseDto 정의
 */

@Data
@Builder
public class ShowInfoRes {
    Long showInfoId;
    String showInfoTitle;
    String showInfoDescription;
    String posterUrl;
    int runningTime;
    Long price;
    UserRes userRes;


    public static ShowInfoRes of(ShowInfo showInfo){
        /**
         * @Method Name : of
         * @작성자 : 금아현
         * @Method 설명 : ShowInfo Entity를 ResponseDto로 변환하는 메소드
         */
        return ShowInfoRes.builder()
                .showInfoId(showInfo.getShowInfoId())
                .showInfoTitle(showInfo.getShowInfoTitle())
                .showInfoDescription(showInfo.getShowInfoDescription())
                .posterUrl(showInfo.getPosterUrl())
                .runningTime(showInfo.getRunningTime())
                .price(showInfo.getPrice())
                .userRes(UserRes.ofWithoutFollowShowInfoReservationVideo(showInfo.getUser()))
                .build();
    }
}
