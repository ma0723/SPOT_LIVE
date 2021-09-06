package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.api.request.showInfo.ShowInfoInsertPostReq;
import com.ssafy.spotlive.api.request.timetable.TimetableInsertPostReq;
import com.ssafy.spotlive.api.request.video.VideoInsertPostReq;
import com.ssafy.spotlive.db.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowInfoRepository showInfoRepository;

    String accountEmail = "newUser@new.com";
    User user;
    Timetable timetable;
    Reservation reservation;
    Long timetableId;

    @BeforeEach
    void setUp() {
        //given
        user = new User();
        user.setAccountEmail(accountEmail);
        userRepository.save(user);

        timetable = new Timetable();
        timetableId = timetableRepository.save(timetable).getTimetableId();

        reservation = new Reservation();
        reservation.setTimetable(timetable);
        reservation.setUser(user);
        reservationRepository.save(reservation);
    }

    @Test
    void deleteReservationByTimetable_TimetableIdTest() {
        //when
        Long exist = reservationRepository.deleteReservationByTimetable_TimetableIdAndUser_AccountEmail(timetableId, accountEmail);
        Long notExist = reservationRepository.deleteReservationByTimetable_TimetableIdAndUser_AccountEmail(-1L, "");

        //then
        assertThat(exist).isEqualTo(1);
        assertThat(notExist).isEqualTo(0);

    }

    @Test
    void existsByTimetable_TimetableIdAndUser_AccountEmailTest() {
        //when
        Boolean isExist1 = reservationRepository.existsByTimetable_TimetableIdAndUser_AccountEmail(timetableId, accountEmail);
        Long notExistTimetableId = -1L;
        Boolean isExist2 = reservationRepository.existsByTimetable_TimetableIdAndUser_AccountEmail(notExistTimetableId, accountEmail);
        String notExistUserAccountEmail = "none";
        Boolean isExist3 = reservationRepository.existsByTimetable_TimetableIdAndUser_AccountEmail(notExistTimetableId, notExistUserAccountEmail);

        //then
        assertThat(isExist1).isTrue();
        assertThat(isExist2).isFalse();
        assertThat(isExist3).isFalse();
    }

    @Test
    void findReservationByUser_AccountEmail(){
        // given
        String insertAccountEmail = "emoney96@naver.com";

        String posterUrl = "posterUrl.png";
        Long price = 10000L;
        int runningTime = 180;
        String showInfoDescription = "description";
        String showInfoTitle = "title";

        ShowInfoInsertPostReq showInfoInsertPostReq = new ShowInfoInsertPostReq();
        showInfoInsertPostReq.setAccountEmail(insertAccountEmail);
        showInfoInsertPostReq.setShowInfoTitle(showInfoTitle);
        showInfoInsertPostReq.setShowInfoDescription(showInfoDescription);
        showInfoInsertPostReq.setPrice(price);
        showInfoInsertPostReq.setRunningTime(runningTime);
        showInfoInsertPostReq.setPosterUrl(posterUrl);
        ShowInfo showInfo = showInfoRepository.save(showInfoInsertPostReq.toShowInfo());

        TimetableInsertPostReq timetableInsertPostReq = new TimetableInsertPostReq();
        timetableInsertPostReq.setDateTime(LocalDateTime.now());
        Timetable timetable = timetableRepository.save(timetableInsertPostReq.toTimetable(showInfo));

        List<Long> timetableIdList = new ArrayList<>();
        timetableIdList.add(timetable.getTimetableId());

        String accountEmail = "kmk130519@naver.com";
        User user = new User();
        user.setAccountEmail(accountEmail);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTimetable(timetable);

        // when
        Reservation reservation1 = reservationRepository.save(reservation);
        List<Reservation> reservationList = reservationRepository.findReservationByUser_AccountEmail(accountEmail).orElse(null);

        // then
        assertThat(reservationList.stream().anyMatch(newReservation -> reservation1.getUser().getAccountEmail().equals(newReservation.getUser().getAccountEmail()) && reservation1.getTimetable().getTimetableId() == newReservation.getTimetable().getTimetableId())).isEqualTo(true);
    }
}