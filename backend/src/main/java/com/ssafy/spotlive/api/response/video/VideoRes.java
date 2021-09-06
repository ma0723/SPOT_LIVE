package com.ssafy.spotlive.api.response.video;

import com.ssafy.spotlive.api.response.category.CategoryRes;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import com.ssafy.spotlive.api.response.timetable.TimetableRes;
import com.ssafy.spotlive.db.entity.Video;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @FileName : VideoRes
 * @작성자 : 권영린
 * @Class 설명 : 유저가 저장한 영상정보
 */
@Getter
@Setter
@Builder
public class VideoRes {
    long videoId;
    String videoTitle;
    String videoDescription;
    String mode;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String thumbnailUrl;
    String videoUrl;
    Boolean isLive;
    Long videoLength;
    Long hit;
    String sessionId;
    ShowInfoRes showInfoRes;
    TimetableRes timetableRes;
    CategoryRes categoryRes;

    public static VideoRes of(Video video) {

        Duration duration = null;
        Long videoLength = null;

        if(video.getEndTime() != null){
            duration = Duration.between(video.getEndTime(), video.getStartTime());
            videoLength = Math.abs(duration.getSeconds());
        }

        if(video.getTimetable() != null) { //공연
            return VideoRes.builder()
                    .videoId(video.getVideoId())
                    .videoTitle(video.getVideoTitle())
                    .videoDescription(video.getVideoDescription())
                    .mode(video.getMode())
                    .startTime(video.getStartTime())
                    .endTime(video.getEndTime())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .videoUrl(video.getVideoUrl())
                    .isLive(video.getIsLive())
                    .hit(video.getHit())
                    .videoLength(videoLength)
                    .sessionId(video.getSessionId())
                    .showInfoRes(ShowInfoRes.of(video.getShowInfo()))
                    .timetableRes(TimetableRes.of(video.getTimetable()))
                    .categoryRes(CategoryRes.of(video.getCategory()))
                    .build();
        } else if(video.getShowInfo() != null) { //홍보
            return VideoRes.builder()
                    .videoId(video.getVideoId())
                    .videoTitle(video.getVideoTitle())
                    .videoDescription(video.getVideoDescription())
                    .mode(video.getMode())
                    .startTime(video.getStartTime())
                    .endTime(video.getEndTime())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .videoUrl(video.getVideoUrl())
                    .isLive(video.getIsLive())
                    .hit(video.getHit())
                    .videoLength(videoLength)
                    .sessionId(video.getSessionId())
                    .showInfoRes(ShowInfoRes.of(video.getShowInfo()))
                    .categoryRes(CategoryRes.of(video.getCategory()))
                    .build();
        } else { //소통
            return VideoRes.builder()
                    .videoId(video.getVideoId())
                    .videoTitle(video.getVideoTitle())
                    .videoDescription(video.getVideoDescription())
                    .mode(video.getMode())
                    .startTime(video.getStartTime())
                    .endTime(video.getEndTime())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .videoUrl(video.getVideoUrl())
                    .isLive(video.getIsLive())
                    .hit(video.getHit())
                    .videoLength(videoLength)
                    .sessionId(video.getSessionId())
                    .categoryRes(CategoryRes.of(video.getCategory()))
                    .build();
        }
    }
}
