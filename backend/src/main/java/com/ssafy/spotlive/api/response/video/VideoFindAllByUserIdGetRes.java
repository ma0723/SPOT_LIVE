package com.ssafy.spotlive.api.response.video;

import com.ssafy.spotlive.db.entity.Video;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @FileName : VideoFindAllByUserIdGetRes
 * @작성자 : 권영린
 * @Class 설명 : 유저 아이디로 영상리스트 조회 요청에 응답할 리스폰스 정의.
 */

@Builder
@Getter
@Setter
@ToString
@ApiModel("VideoFindAllByUserIdGetRes")
public class VideoFindAllByUserIdGetRes {
    String videoTitle;
    String videoDescription;
    String mode;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String thumbnailUrl;
    String videoUrl;
    Boolean isLive;
    Long hit;
    String sessionId;
    Long showInfoId;
    Long categoryId;
    String accountEmail;

    public static VideoFindAllByUserIdGetRes of(Video video) {
        /**
         * @Method Name : of
         * @작성자 : 권영린
         * @Method 설명 : Video Entity를 VideoInsertPostResDto로 변환하는 메소드
         */
        if(video.getShowInfo()==null){
            return VideoFindAllByUserIdGetRes
                    .builder()
                    .videoTitle(video.getVideoTitle())
                    .videoDescription(video.getVideoDescription())
                    .mode(video.getMode())
                    .startTime(video.getStartTime())
                    .endTime(video.getEndTime())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .videoUrl(video.getVideoUrl())
                    .isLive(video.getIsLive())
                    .hit(video.getHit())
                    .sessionId(video.getSessionId())
//                    .showInfoId(video.getShowInfo().getShowInfoId())
                    .categoryId(video.getCategory().getCategoryId())
                    .accountEmail(video.getUser().getAccountEmail())
                    .build();
        } else {
            return VideoFindAllByUserIdGetRes
                    .builder()
                    .videoTitle(video.getVideoTitle())
                    .videoDescription(video.getVideoDescription())
                    .mode(video.getMode())
                    .startTime(video.getStartTime())
                    .endTime(video.getEndTime())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .videoUrl(video.getVideoUrl())
                    .isLive(video.getIsLive())
                    .hit(video.getHit())
                    .sessionId(video.getSessionId())
                    .showInfoId(video.getShowInfo().getShowInfoId())
                    .categoryId(video.getCategory().getCategoryId())
                    .accountEmail(video.getUser().getAccountEmail())
                    .build();
        }
    }

}
