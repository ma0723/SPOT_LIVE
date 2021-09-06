package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.Reservation;
import com.ssafy.spotlive.db.entity.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @FileName : ReservationRepository
 * @작성자 : 금아현
 * @Class 설명 : Reservation 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    /**
     * @Method Name : deleteReservationByTimetable_TimetableIdAndUser_AccountEmail
     * @작성자 : 금아현
     * @Method 설명 : reservation의 id로 예약 정보 삭제
     */
    @Transactional
    Long deleteReservationByTimetable_TimetableIdAndUser_AccountEmail(Long timetable_timetableId, String user_accountEmail);

    /**
     * @Method Name : existsByTimetable_TimetableIdAndUser_AccountEmail
     * @작성자 : 금아현
     * @Method 설명 : reservation의 id로 예약 여부 확인
     */
    Boolean existsByTimetable_TimetableIdAndUser_AccountEmail(Long timetable_timetableId, String user_accountEmail);

    /**
     * @Method Name : findReservationByUser_AccountEmail
     * @작성자 : 강용수
     * @Method 설명 : accountEmail 기준으로 reservationList를 조회하는 메소드
     */
    Optional<List<Reservation>> findReservationByUser_AccountEmail(String accountEmail);
}
