package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.timetable.TimetableRes;
import com.ssafy.spotlive.db.entity.Timetable;
import com.ssafy.spotlive.db.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @FileName : TimetableServiceImpl
 * @작성자 : 금아현
 * @Class 설명 : Timetable 관련 비즈니스 로직처리를 위한 서비스 구현 정의
 */
@Service
public class TimetableServiceImpl implements TimetableService{

    @Autowired
    TimetableRepository timetableRepository;

    @Override
    public TimetableRes findTimetableByShowInfoId(Long showInfoId) {
        /**
         * @Method Name : findTimetableByShowInfoId
         * @작성자 : 금아현
         * @Method 설명 : 현재 시간 30분 전후의 공연시간 조회
         */
        LocalDateTime start = LocalDateTime.now().minusMinutes(30);
        LocalDateTime end = LocalDateTime.now().plusMinutes(30);
        Optional<Timetable> optionalTimetable = timetableRepository.findTimetableByShowInfo_ShowInfoIdAndDateTimeBetween(showInfoId, start, end);
        return optionalTimetable.map(TimetableRes::of).orElse(null);
    }
}
