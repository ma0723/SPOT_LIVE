package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.showInfo.ShowInfoUpdatePatchReq;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoFindByIdGetRes;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import com.ssafy.spotlive.db.entity.ShowInfo;
import com.ssafy.spotlive.db.repository.ShowInfoRepository;
import com.ssafy.spotlive.db.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @FileName : ShowInfoServiceImpl
 * @작성자 : 금아현
 * @Class 설명 : 공연 정보 관련 비즈니스 로직처리를 위한 서비스 구현 정의
 */
@Service
public class ShowInfoServiceImpl implements ShowInfoService {

    @Autowired
    ShowInfoRepository showInfoRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    FileUploadService fileUploadService;

    @Override
    public void insertShowInfo(ShowInfoInsertPostReq showInfoInsertPostReq, MultipartFile posterImage) {
        /**
         * @Method Name : insertShowInfo
         * @작성자 : 금아현
         * @Method 설명 : 공연정보 추가
         */
        String posterImageUrl = null;
        try {
            /* S3에 업로드 */
            posterImageUrl = fileUploadService.upload(posterImage);
        } catch(Exception e) {
            e.printStackTrace();
        }
        showInfoInsertPostReq.setPosterUrl(posterImageUrl);
        Long id = showInfoRepository.save(showInfoInsertPostReq.toShowInfo()).getShowInfoId();
        ShowInfo showInfo = showInfoRepository.getById(id);
        showInfoInsertPostReq.getTimetableInsertPostReq().forEach(timetableInsertPostReq -> timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo)));
    }

    @Override
    public ShowInfoFindByIdGetRes findShowInfoById(long id) {
        /**
         * @Method Name : findShowInfoById
         * @작성자 : 금아현
         * @Method 설명 : 공연 정보를 ID로 조회
         */
        Optional<ShowInfo> optionalShowInfo = showInfoRepository.findShowInfoByShowInfoId(id);
        return optionalShowInfo.map(ShowInfoFindByIdGetRes::of).orElse(null);
    }

    @Override
    public Long deleteShowInfoById(long id) {
        /**
         * @Method Name : deleteShowInfoById
         * @작성자 : 금아현
         * @Method 설명 : 공연정보를 id로 삭제
         */
        return showInfoRepository.deleteShowInfoByShowInfoId(id);
    }

    @Override
    public Boolean updateShowInfoById(long id, ShowInfoUpdatePatchReq showInfoUpdatePatchReq, MultipartFile posterImage) {
        /**
         * @Method Name : updateShowInfoById
         * @작성자 : 금아현
         * @Method 설명 : 공연정보를 id로 수정
         */
        ShowInfo showInfo = showInfoRepository.findShowInfoByShowInfoId(id).orElse(null);
        if(showInfo == null) return Boolean.FALSE;

        if (posterImage != null && !posterImage.isEmpty()) {
            String nextPosterImageUrl = null;
            String currentPosterUrl = showInfo.getPosterUrl();
            if (currentPosterUrl != null)
                fileUploadService.delete(currentPosterUrl);
            try {
                nextPosterImageUrl = fileUploadService.upload(posterImage);
                showInfo.setPosterUrl(nextPosterImageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (showInfoUpdatePatchReq.getShowInfoTitle() != null)
            showInfo.setShowInfoTitle(showInfoUpdatePatchReq.getShowInfoTitle());
        if (showInfoUpdatePatchReq.getShowInfoDescription() != null)
            showInfo.setShowInfoDescription(showInfoUpdatePatchReq.getShowInfoDescription());
        if (showInfoUpdatePatchReq.getPrice() != null)
            showInfo.setPrice(showInfoUpdatePatchReq.getPrice());
        if (showInfoUpdatePatchReq.getRunningTime() != 0)
            showInfo.setRunningTime(showInfoUpdatePatchReq.getRunningTime());

        if (showInfoUpdatePatchReq.getTimetableInsertPostReq() != null) {
            showInfoRepository.save(showInfo);
            timetableRepository.deleteAllByShowInfo_ShowInfoId(id);
            showInfoUpdatePatchReq.getTimetableInsertPostReq().forEach(timetableInsertPostReq -> timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo)));
        }
        return Boolean.TRUE;
    }

    @Override
    public List<ShowInfoRes> findShowInfoByUser(String accountEmail) {
        /**
         * @Method Name : findShowInfoByUser
         * @작성자 : 금아현
         * @Method 설명 : 공연정보를 유저 이메일로 조회
         */
        Optional<List<ShowInfo>> optionalShowInfo = showInfoRepository.findShowInfosByUser_AccountEmail(accountEmail);
        return optionalShowInfo.map(showInfos -> showInfos.stream().map(showInfo -> ShowInfoRes.of(showInfo)).collect(Collectors.toList())).orElse(null);
    }
}