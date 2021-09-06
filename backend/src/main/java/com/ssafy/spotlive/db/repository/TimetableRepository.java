package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @FileName : TimetableRepository
 * @작성자 : 금아현
 * @Class 설명 : Timetable 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    /**
     * @Method Name : deleteAllByShowInfo_ShowInfoId
     * @작성자 : 금아현
     * @Method 설명 : show_info_id가 id인 timetable 전부 삭제
     */
    @Transactional
    void deleteAllByShowInfo_ShowInfoId(Long id);

    /**
     * @Method Name : findTimetableByShowInfo_ShowInfoIdAndDateTimeBetween
     * @작성자 : 금아현
     * @Method 설명 : 시간이 start와 end 사이인 timetable 조회
     */
    Optional<Timetable> findTimetableByShowInfo_ShowInfoIdAndDateTimeBetween(Long showInfo_showInfoId, LocalDateTime start, LocalDateTime end);

    /**
     * @Method Name : findTimetablesByShowInfo_ShowInfoId
     * @작성자 : 강용수
     * @Method 설명 : 해당 공연의 timetable을 조회하는 메소드
     */
    Optional<List<Timetable>> findTimetablesByShowInfo_ShowInfoId(Long showInfoId);
}
