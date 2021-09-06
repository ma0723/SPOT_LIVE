package com.ssafy.spotlive.api.response.timetable;

import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import com.ssafy.spotlive.db.entity.Timetable;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @FileName : TimetableFindByReservationRes
 * @작성자 : 금아현
 * @Class 설명 : 예약 정보로 timetable을 확인하기 위한 ResponseDto 정의
 */
@Getter
@Setter
@Builder
@ApiModel("TimetableFindByReservationRes")
public class TimetableFindByReservationRes {
    Long timetableId;
    LocalDateTime dateTime;
    ShowInfoRes showInfoRes;

    public static TimetableFindByReservationRes of(Timetable timetable){
        /**
         * @Method Name : of
         * @작성자 : 금아현
         * @Method 설명 : Timetable Entity를 ResponseDto로 변환하는 메소드
         */
        return TimetableFindByReservationRes.builder()
                .timetableId(timetable.getTimetableId())
                .dateTime(timetable.getDateTime())
                .showInfoRes(ShowInfoRes.of(timetable.getShowInfo()))
                .build();
    }
}