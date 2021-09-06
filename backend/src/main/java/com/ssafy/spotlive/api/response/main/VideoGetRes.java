package com.ssafy.spotlive.api.response.main;

import com.ssafy.spotlive.db.entity.Video;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @FileName : VideoGetRes
 * @작성자 : 강용수
 * @Class 설명 : 메인 화면 내 Video 조회 요청에 대한 VideoGetResponseDto 정의
 *              실제로 데이터가 전송되는 ResponseDto
 */
@Getter
@Setter
@ToString
@Builder
public class VideoGetRes {
    List<VideoFindMainVideoRes> videoResList;
    boolean empty; // 데이터가 없는지
    boolean first; // 첫 페이지인지
    boolean last; // 마지막 페이지인지
    int number; // 현재 페이지 번호 (1페이지 -> 0으로 표시됨)
    int numberOfElements; // 요청 페이지에서 조회 된 데이터의 갯수
    Pageable pageable;
    int size; // 페이지 당 출력 갯수
    Sort sort;
    long totalElements; // 조회된 데이터의 전체 갯수
    int totalPages; // 전체 페이지의 갯수 (ex. 조회된 데이터가 10개, 페이지 당 출력 갯수가 3개 -> totalPages = 4)

    public static VideoGetRes of(Page<Video> pageVideo, Pageable pageable, Sort sort){
        /**
         * @Method Name : of
         * @작성자 : 강용수
         * @Method 설명 : 페이지화된 pageVideo, Pageable 객체, Sort 객체를 받아 VideoGetResponseDto로 변환하는 함수
         */
        return VideoGetRes.builder()
                .videoResList(pageVideo.stream().map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList()))
                .empty(pageVideo.isEmpty())
                .first(pageVideo.isFirst())
                .last(pageVideo.isLast())
                .number(pageVideo.getNumber())
                .numberOfElements(pageVideo.getNumberOfElements())
                .pageable(pageable)
                .size(pageVideo.getSize())
                .sort(sort)
                .totalElements(pageVideo.getTotalElements())
                .totalPages(pageVideo.getTotalPages())
                .build();
    }
}