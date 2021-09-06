package com.ssafy.spotlive.api.response.video;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : VideoOpenViduSessionGetRes
 * @작성자 : 김민권
 * @Class 설명 : Openvidu 세션, 토큰을 얻는 API 요청의 Response
 */
@Builder
@Getter
@Setter
@ToString
@ApiModel("VideoOpenViduSessionGetRes")
public class VideoOpenViduSessionGetRes {
    String sessionId;
    String token;
}
