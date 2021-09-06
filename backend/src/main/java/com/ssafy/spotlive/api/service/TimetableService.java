package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.timetable.TimetableRes;

/**
 * @FileName : TimetableService
 * @작성자 : 금아현
 * @Class 설명 : Timetable 관련 비즈니스 로직처리를 위한 서비스 인터페이스 정의
 */
public interface TimetableService {
    TimetableRes findTimetableByShowInfoId(Long showInfoId);
}
