package com.ssafy.spotlive.api.response.timetable;

import com.ssafy.spotlive.db.entity.Timetable;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @FileName : TimetableRes
 * @작성자 : 금아현
 * @Class 설명 : 단일 공연 정보 조회 API 요청에 대한 ResponseDto 정의
 */
@Getter
@Setter
@Builder
@ApiModel("TimetableRes")
public class TimetableRes {
    Long timetableId;
    LocalDateTime dateTime;

    public static TimetableRes of(Timetable timetable){
        /**
         * @Method Name : of
         * @작성자 : 금아현
         * @Method 설명 : Timetable Entity를 ResponseDto로 변환하는 메소드
         */
        return TimetableRes.builder()
                .timetableId(timetable.getTimetableId())
                .dateTime(timetable.getDateTime())
                .build();
    }
}
