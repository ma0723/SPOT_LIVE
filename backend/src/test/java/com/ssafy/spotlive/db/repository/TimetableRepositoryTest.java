package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.ShowInfo;
import com.ssafy.spotlive.db.entity.Timetable;
import com.ssafy.spotlive.db.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class TimetableRepositoryTest {

    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private ShowInfoRepository showInfoRepository;
    @Autowired
    private  UserRepository userRepository;

    String accountEmail;
    User user;
    ShowInfo showInfo;
    Long showInfoId;

    @BeforeEach
    void setUp() {
        //given
        accountEmail = "newUser@new.com";
        user = new User();
        user.setAccountEmail(accountEmail);
        userRepository.save(user);

        showInfo = new ShowInfo();
        showInfo.setUser(user);
        showInfoId = showInfoRepository.save(showInfo).getShowInfoId();

        for(int i = 0; i < 3; i++){
            Timetable timetable = new Timetable();
            timetable.setShowInfo(showInfo);
            timetable.setDateTime(LocalDateTime.now().plusHours(i));
            timetableRepository.save(timetable);
        }
    }

    @Test
    void deleteAllByShowInfo_ShowInfoIdTest() {
        //when
        timetableRepository.deleteAllByShowInfo_ShowInfoId(showInfoId);
        List<Timetable> timetableList = timetableRepository.findTimetablesByShowInfo_ShowInfoId(showInfoId).orElse(null);
        assert timetableList != null;

        //then
        assertThat(timetableList.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void findTimetableByShowInfo_ShowInfoIdAndDateTimeBetweenTest() {
        //when
        Timetable timetable = timetableRepository.findTimetableByShowInfo_ShowInfoIdAndDateTimeBetween(showInfoId, LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(30)).orElse(null);

        //then
        assertThat(timetable).isNotNull();
    }

    @Test
    void findTimetablesByShowInfo_ShowInfoIdTest() {
        //when
        List<Timetable> timetableList = timetableRepository.findTimetablesByShowInfo_ShowInfoId(showInfoId).orElse(null);
        assert timetableList != null;

        //then
        assertThat(timetableList.size()).isEqualTo(3);
    }
}