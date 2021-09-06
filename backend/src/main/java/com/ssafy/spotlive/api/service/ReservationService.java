package com.ssafy.spotlive.api.service;

/**
 * @FileName : ReservationService
 * @작성자 : 금아현
 * @Class 설명 : 예약 정보 관련 비즈니스 로직처리를 위한 서비스 인터페이스 정의
 */
public interface ReservationService {
    Boolean insertReservation(String accountEmail, Long timetableId);
    Long deleteReservationById(String accessToken, long timetableId);
    Boolean findReservationById(long timetableId, String accountEmail);
}
