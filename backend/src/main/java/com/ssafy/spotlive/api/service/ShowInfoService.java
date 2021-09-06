package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.showInfo.ShowInfoUpdatePatchReq;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoFindByIdGetRes;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @FileName : ShowInfoService
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보 관련 비즈니스 로직처리를 위한 서비스 인터페이스 정의
 */
public interface ShowInfoService {
    void insertShowInfo(ShowInfoInsertPostReq showInfoInsertPostReq, MultipartFile posterImage);
    ShowInfoFindByIdGetRes findShowInfoById(long id);
    Long deleteShowInfoById(long id);
    Boolean updateShowInfoById(long id, ShowInfoUpdatePatchReq showInfoUpdatePatchReq, MultipartFile posterImage);
    List<ShowInfoRes> findShowInfoByUser(String accountEmail);
}
