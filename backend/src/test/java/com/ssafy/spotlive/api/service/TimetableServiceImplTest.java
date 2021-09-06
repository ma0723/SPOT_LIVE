package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.db.repository.TimetableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verify;

@Transactional
@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {

    @Mock
    private TimetableRepository timetableRepository;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    @Test
    void findTimetableByShowInfoId() {
        //given
        Long showInfoId = 1L;
        //when
        timetableService.findTimetableByShowInfoId(showInfoId);
        //then
        verify(timetableRepository).findTimetableByShowInfo_ShowInfoIdAndDateTimeBetween(anyObject(), anyObject(), anyObject());
    }
}