package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.db.entity.Reservation;
import com.ssafy.spotlive.db.entity.Timetable;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.repository.ReservationRepository;
import com.ssafy.spotlive.db.repository.TimetableRepository;
import com.ssafy.spotlive.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName : ReservationServiceImpl
 * @작성자 : 금아현
 * @Class 설명 : 예약 정보 관련 비즈니스 로직처리를 위한 서비스 구현 정의
 */
@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Override
    public Boolean insertReservation(String accountEmail, Long timetableId) {
        /**
         * @Method Name : insertReservation
         * @작성자 : 금아현
         * @Method 설명 : 공연 예약 정보 추가
         */
        Reservation reservation = new Reservation();
        User user = userRepository.findById(accountEmail).orElse(null);
        Timetable timetable = timetableRepository.findById(timetableId).orElse(null);
        if(user == null || timetable == null) return Boolean.FALSE;
        reservation.setUser(user);
        reservation.setTimetable(timetable);
        reservationRepository.save(reservation);
        return Boolean.TRUE;
    }

    @Override
    public Long deleteReservationById(String accessToken, long timetableId) {
        /**
         * @Method Name : deleteReservationById
         * @작성자 : 금아현
         * @Method 설명 : 예약 정보를 id로 삭제
         */
        String accountEmail = userRepository.findUserByAccessToken(accessToken).get().getAccountEmail();
        return reservationRepository.deleteReservationByTimetable_TimetableIdAndUser_AccountEmail(timetableId, accountEmail);
   }

    @Override
    public Boolean findReservationById(long timetableId, String accountEmail) {
        /**
         * @Method Name : findReservationById
         * @작성자 : 금아현
         * @Method 설명 : timetableId, accountEmail로 예약 여부 확인
         */
        return reservationRepository.existsByTimetable_TimetableIdAndUser_AccountEmail(timetableId, accountEmail);
    }
}