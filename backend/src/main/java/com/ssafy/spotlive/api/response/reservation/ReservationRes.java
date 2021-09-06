package com.ssafy.spotlive.api.response.reservation;

import com.ssafy.spotlive.api.response.timetable.TimetableFindByReservationRes;
import com.ssafy.spotlive.db.entity.Reservation;
import lombok.Builder;
import lombok.Data;

/**
 * @FileName : ReservationRes
 * @작성자 : 금아현
 * @Class 설명 : 예약정보에 대한 ResponseDto 정의
 */

@Data
@Builder
public class ReservationRes {
    TimetableFindByReservationRes timetableFindByReservationRes;
    public static ReservationRes of(Reservation reservation){
        /**
         * @Method Name : of
         * @작성자 : 금아현
         * @Method 설명 : Reservation Entity를 ResponseDto로 변환하는 메소드
         */
        return ReservationRes.builder()
                .timetableFindByReservationRes(TimetableFindByReservationRes.of(reservation.getTimetable()))
                .build();
    }
}
