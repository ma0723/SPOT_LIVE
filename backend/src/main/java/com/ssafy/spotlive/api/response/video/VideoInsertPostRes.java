package com.ssafy.spotlive.api.response.video;

import com.ssafy.spotlive.db.entity.Video;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : VideoInsertPostRes
 * @작성자 : 권영린
 * @Class 설명 : 스트리밍 시작 API 요청에 응답할 리스폰스 정의.
 */
@Builder
@Getter
@Setter
@ToString
@ApiModel("VideoInsertPostRes")
public class VideoInsertPostRes {
    long videoId;
    String thumbnailUrl;
    String sessionId;
    String token;

    public static VideoInsertPostRes of(Video video) {
        /**
         * @Method Name : of
         * @작성자 : 권영린
         * @Method 설명 : Video Entity를 VideoInsertPostResDto로 변환하는 메소드
         */
        return VideoInsertPostRes.builder()
                .videoId(video.getVideoId())
                .thumbnailUrl(video.getThumbnailUrl())
                .sessionId(video.getSessionId())
                .build();
    }
}
