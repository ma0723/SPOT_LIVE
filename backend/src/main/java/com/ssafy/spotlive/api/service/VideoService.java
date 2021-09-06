package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.video.VideoInsertPostReq;
import com.ssafy.spotlive.api.request.video.VideoInsertUrlByIdPostReq;
import com.ssafy.spotlive.api.request.video.VideoUpdateByIdPatchReq;
import com.ssafy.spotlive.api.response.video.VideoFindAllByUserIdGetRes;
import com.ssafy.spotlive.api.response.video.VideoFindByIdGetRes;
import com.ssafy.spotlive.api.response.video.VideoInsertPostRes;
import com.ssafy.spotlive.api.response.video.VideoOpenViduSessionGetRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @FileName : VideoService
 * @작성자 : 권영린
 * @Class 설명 : Video관련 기능을 위한 Service 정의.
 */
public interface VideoService {
    VideoInsertPostRes insertVideo(VideoInsertPostReq videoInsertPostReq, MultipartFile thumbnailImage);
    VideoFindByIdGetRes findVideoById(Long id);
    Boolean updateVideoById(Long videoId, MultipartFile thumbnailImage, VideoUpdateByIdPatchReq videoUpdateByIdPatchReq, String accountEmail);
    void deleteVideoById(long videoId);
    Boolean updateVideoEndTimeById(Long videoId, String accountEmail);
    List<VideoFindAllByUserIdGetRes> findVideoByAccountEmail(String accountEmail);
    VideoFindByIdGetRes insertRecordUrlById(VideoInsertUrlByIdPostReq videoInsertUrlByIdPostReq);

    String createSession();
    VideoOpenViduSessionGetRes createToken(String sessionId);
    int closeSession(String sessionId);
    Boolean updateVideoHitPlusById(Long videoId);
    Boolean updateVideoHitMinusById(Long videoId);

}
