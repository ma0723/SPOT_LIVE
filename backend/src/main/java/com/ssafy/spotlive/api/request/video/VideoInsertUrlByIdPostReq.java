package com.ssafy.spotlive.api.request.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : VideoInsertUrlByIdPostReq
 * @작성자 : 김민권
 * @Class 설명 : 비디오 URL 저장 API 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("VideoInsertUrlByIdPostReq")
public class VideoInsertUrlByIdPostReq {
    @ApiModelProperty(name="videoId : 저장할 비디오의 PK", example="1")
    Long videoId;
    @ApiModelProperty(name="videoUrl : 다시보기 URL", example="https://i5a405.p.ssafy.io/openvidu/recordings/session13184628/REC_session13184628_2021811.mp4")
    String videoUrl;
}
