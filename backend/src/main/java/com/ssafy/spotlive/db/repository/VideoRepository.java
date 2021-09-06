package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @FileName : VideoRepository
 * @작성자 : 권영린
 * @Class 설명 : Video 관련 query문 생성을 위한 JPARepository 정의.
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    /**
     * @Method Name : findVideosByMode
     * @작성자 : 강용수
     * @Method 설명 : mode(홍보 / 소통 / 공연)를 기준으로 Video를 검색하는 메소드
     */
    Page<Video> findVideosByMode(Pageable pageable, String mode);

    /**
     * @Method Name : findVideosByModeAndCategoryId
     * @작성자 : 강용수
     * @Method 설명 : mode(홍보 / 소통 / 공연)와 카테고리 id 기준으로 Video를 검색하는 메소드
     */
    Page<Video> findVideosByModeAndCategory_CategoryId(Pageable pageable, String mode, Long categoryId);

    /**
     * @Method Name : findVideosByIsLive
     * @작성자 : 강용수
     * @Method 설명 : Video를 조회수(다시보기) / 시청자(라이브) 순으로 검색하는 메소드
     */
    Page<Video> findVideosByIsLiveOrderByHitDesc(Pageable pageable, Boolean isLive);

    /**
     * @Method Name : findVideosByIsLiveAndCategory_CategoryId
     * @작성자 : 강용수
     * @Method 설명 : Video를 카테고리 id 기준과 조회수(다시보기) / 시청자(라이브) 순으로 검색하는 메소드
     */
    Page<Video> findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(Pageable pageable, Boolean isLive, Long categoryId);

    /**
     * @Method Name : findVideosByUser_AccountEmailIn
     * @작성자 : 강용수
     * @Method 설명 : 자신이 팔로우한 유저의 Video를 검색하는 메소드
     */
    Page<Video> findVideosByUser_AccountEmailIn(Pageable pageable, List<String> accountEmailList);

    /**
     * @Method Name : findVideosByCategory_CategoryIdAndUser_AccountEmailIn
     * @작성자 : 강용수
     * @Method 설명 : 자신이 팔로우한 유저의 Video를 카테고리 id 기준으로 검색하는 메소드
     */
    Page<Video> findVideosByCategory_CategoryIdAndUser_AccountEmailIn(Pageable pageable, Long categoryId, List<String> accountEmailList);

    /**
     * @Method Name : findVideosByUserAccountEmail
     * @작성자 : 권영린
     * @Method 설명 : 이메일로 해당 유저의 Video를 검색하는 메소드
     */
    List<Video> findVideosByUserAccountEmail(String accountEmail);

    /**
     * @Method Name : findVideosByMode
     * @작성자 : 강용수
     * @Method 설명 : mode(홍보 / 소통 / 공연)를 기준으로 Video를 검색하는 메소드
     */
    Optional<List<Video>> findVideosByMode(String mode);

    /**
     * @Method Name : findVideosByIsLive
     * @작성자 : 강용수
     * @Method 설명 : Video를 조회수(다시보기) / 시청자(라이브) 순으로 검색하는 메소드
     */
    Optional<List<Video>> findVideosByIsLive(Boolean isLive);

    /**
     * @Method Name : findVideosByUser_AccountEmailIn
     * @작성자 : 강용수
     * @Method 설명 : 자신이 팔로우한 유저의 Video를 검색하는 메소드
     */
    Optional<List<Video>> findVideosByUser_AccountEmailIn(List<String> accountEmailList);

    /**
     * @Method Name : findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains
     * @작성자 : 강용수
     * @Method 설명 : 검색 키워드가 영상 제목이나 설명이나 닉네임에 포함된 Video를 검색하는 메소드
     */
    Page<Video> findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(Pageable pageable, String videoTitle, String videoDescription, String profileNickname);

    /**
     * @Method Name : findVideosByModeAndTimetable_TimetableIdIn
     * @작성자 : 강용수
     * @Method 설명 : mode가 공연인 영상 중본인이 예약한 공연의 videoList를 조회하는 메소드
     */
    Optional<List<Video>> findVideosByModeAndTimetable_TimetableIdIn(String mode, List<Long> timetableIdList);
}