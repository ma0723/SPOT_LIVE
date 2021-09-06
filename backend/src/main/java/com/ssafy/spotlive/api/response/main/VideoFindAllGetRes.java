package com.ssafy.spotlive.api.response.main;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @FileName : VideoGetRes
 * @작성자 : 강용수
 * @Class 설명 : 메인 화면 진입 시 모든 Video 조회 요청에 대한 VideoFindAllGetResponseDto 정의
 *              실제로 데이터가 전송되는 ResponseDto
 */
@Getter
@Setter
@ToString
@Builder
public class VideoFindAllGetRes {
    VideoGetRes adVideoGetRes; // 홍보
    VideoGetRes talkVideoGetRes; // 소통
    VideoGetRes showVideoGetRes; // 공연
    VideoGetRes replayVideoGetRes; // 다시보기
    VideoGetRes liveVideoGetRes; // 라이브
    VideoGetRes followVideoGetRes; // 팔로우
    List<VideoFindMainVideoRes> reservationVideoGetResList; // 내가 예약한 공연

    public static VideoFindAllGetRes of(
            VideoGetRes adVideoGetRes,
            VideoGetRes talkVideoGetRes,
            VideoGetRes showVideoGetRes,
            VideoGetRes replayVideoGetRes,
            VideoGetRes liveVideoGetRes,
            VideoGetRes followVideoGetRes,
            List<VideoFindMainVideoRes> reservationVideoGetResList) {
        /**
         * @Method Name : of
         * @작성자 : 강용수
         * @Method 설명 : VideoGetResponseDto를 VideoFindAllGetRes로 변환하는 함수
         */
        return VideoFindAllGetRes.builder()
                .adVideoGetRes(adVideoGetRes)
                .talkVideoGetRes(talkVideoGetRes)
                .showVideoGetRes(showVideoGetRes)
                .replayVideoGetRes(replayVideoGetRes)
                .liveVideoGetRes(liveVideoGetRes)
                .followVideoGetRes(followVideoGetRes)
                .reservationVideoGetResList(reservationVideoGetResList)
                .build();
    }
}