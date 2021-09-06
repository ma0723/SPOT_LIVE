package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.showInfo.ShowInfoUpdatePatchReq;
import com.ssafy.spotlive.api.request.timetable.TimetableInsertPostReq;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoFindByIdGetRes;
import com.ssafy.spotlive.api.response.showInfo.ShowInfoRes;
import com.ssafy.spotlive.db.entity.ShowInfo;
import com.ssafy.spotlive.db.entity.Timetable;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.repository.ShowInfoRepository;
import com.ssafy.spotlive.db.repository.TimetableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class ShowInfoServiceImplTest {

    @Mock
    ShowInfoRepository showInfoRepository;

    @Mock
    FileUploadService fileUploadService;

    @Mock
    MultipartFile multipartFile;

    @Mock
    TimetableRepository timetableRepository;

    @InjectMocks
    ShowInfoService showInfoService = new ShowInfoServiceImpl();

    @Test
    void insertShowInfoTest() throws IOException {
        // given
        TimetableInsertPostReq timetableInsertPostReq1 = new TimetableInsertPostReq();
        timetableInsertPostReq1.setDateTime(LocalDateTime.now());
        TimetableInsertPostReq timetableInsertPostReq2 = new TimetableInsertPostReq();
        timetableInsertPostReq2.setDateTime(LocalDateTime.now());

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setTimetableInsertPostReq(new ArrayList<>());
        showInfoInsertPostReq.getTimetableInsertPostReq().add(timetableInsertPostReq1);
        showInfoInsertPostReq.getTimetableInsertPostReq().add(timetableInsertPostReq2);

        ShowInfo showInfo = new ShowInfo();
        showInfo.setShowInfoId(1L);
        String posterImageUrl = "posterUrl.png";

        given(showInfoRepository.save(anyObject())).willReturn(showInfo);
        given(showInfoRepository.getById(1L)).willReturn(showInfo);
        given(fileUploadService.upload(multipartFile)).willReturn(posterImageUrl);
        // when
        showInfoService.insertShowInfo(showInfoInsertPostReq, multipartFile);
        // then
        verify(showInfoRepository).save(anyObject());
        verify(showInfoRepository).getById(1L);
        verify(timetableRepository, times(2)).save(anyObject());
    }
    
    @Test
    void findShowInfoByIdTest() {
        //given
        String accountEmail = "accountEmail";
        User user = new User();
        user.setAccountEmail(accountEmail);
        ShowInfo showInfo = new ShowInfo();
        showInfo.setUser(user);
        showInfo.setShowInfoId(1L);
        given(showInfoRepository.findShowInfoByShowInfoId(1L)).willReturn(Optional.of(showInfo));
        //when
        ShowInfoFindByIdGetRes showInfoFindByIdGetRes = showInfoService.findShowInfoById(1L);
        //then
        assertEquals(showInfo.getShowInfoId(), showInfoFindByIdGetRes.getShowInfoId());
        assertThat(showInfoFindByIdGetRes).isNotNull();
        verify(showInfoRepository).findShowInfoByShowInfoId(anyLong());
    }

    @Test
    void deleteShowInfoByIdTest() {
        //given
        ShowInfo showInfo = new ShowInfo();
        long showInfoId = 1L;
        showInfo.setShowInfoId(showInfoId);
        //when
        showInfoService.deleteShowInfoById(showInfoId);
        //then
        verify(showInfoRepository, times(1)).deleteShowInfoByShowInfoId(anyLong());
    }

    @Test
    void updateShowInfoByIdTest() {
        //given
        ShowInfo showInfo = new ShowInfo();
        long showInfoId = 1L;
        showInfo.setShowInfoId(showInfoId);

        TimetableInsertPostReq timetableInsertPostReq1 = new TimetableInsertPostReq();
        timetableInsertPostReq1.setDateTime(LocalDateTime.now());
        TimetableInsertPostReq timetableInsertPostReq2 = new TimetableInsertPostReq();
        timetableInsertPostReq2.setDateTime(LocalDateTime.now());

        ShowInfoUpdatePatchReq showInfoUpdatePatchReq = new ShowInfoUpdatePatchReq();
        showInfoUpdatePatchReq.setTimetableInsertPostReq(new ArrayList<>());
        showInfoUpdatePatchReq.getTimetableInsertPostReq().add(timetableInsertPostReq1);
        showInfoUpdatePatchReq.getTimetableInsertPostReq().add(timetableInsertPostReq2);

        given(showInfoRepository.findShowInfoByShowInfoId(1L)).willReturn(Optional.of(showInfo));
        //when
        Boolean isSuccess = showInfoService.updateShowInfoById(showInfoId, showInfoUpdatePatchReq, multipartFile);
        //then
        assertThat(isSuccess).isTrue();
        verify(showInfoRepository).save(any(ShowInfo.class));
        verify(timetableRepository).deleteAllByShowInfo_ShowInfoId(showInfoId);
        verify(timetableRepository, times(2)).save(any(Timetable.class));
    }

    @Test
    void findShowInfoByUserTest() {
        //given
        String accountEmail = "accountEmail";
        User user = new User();
        user.setAccountEmail(accountEmail);
        ShowInfo showInfo = new ShowInfo();
        showInfo.setUser(user);
        List<ShowInfo> showInfoList = new ArrayList<>();
        showInfoList.add(showInfo);
        given(showInfoRepository.findShowInfosByUser_AccountEmail(accountEmail)).willReturn(Optional.of(showInfoList));
        //when
        List<ShowInfoRes> showInfoResList = showInfoService.findShowInfoByUser(accountEmail);
        //then
        assertEquals(user.getAccountEmail(), showInfoResList.get(0).getUserRes().getAccountEmail());
        verify(showInfoRepository).findShowInfosByUser_AccountEmail(any(String.class));
    }
}