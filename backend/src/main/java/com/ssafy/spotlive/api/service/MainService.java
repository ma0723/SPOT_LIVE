package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.main.UserFindFollowGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindAllGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindMainVideoRes;
import com.ssafy.spotlive.api.response.main.VideoGetRes;

import java.util.List;

/**
 * @FileName : MainService
 * @작성자 : 강용수
 * @Class 설명 : 메인화면 데이터 조회를 위한 Service 정의.
 */
public interface MainService {
    VideoFindAllGetRes findAllVideo(int page, int size, Long categoryId, String accountEmail);
    VideoGetRes findAllVideoByModeAndCategoryId(int page, int size, Long categoryId, String mode);
    VideoGetRes findAllReplayVideoByIsLiveAndCategoryId(int page, int size, Long categoryId);
    VideoGetRes findAllLiveVideoByIsLiveAndCategoryId(int page, int size, Long categoryId);
    VideoGetRes findAllFollowVideoByCategoryId(int page, int size, Long categoryId, String accountEmail);
    List<UserFindFollowGetRes> findAllFollowByFan(String accountEmail);
    List<VideoFindMainVideoRes> findAllTopVideo(String accountEmail);
    VideoGetRes findAllSearchVideoByKeywordContains(int page, int size, String keyword);
    List<VideoFindMainVideoRes> findAllReservationVideoByModeAndTimetableIdIn(String mode, String accountEmail);
}