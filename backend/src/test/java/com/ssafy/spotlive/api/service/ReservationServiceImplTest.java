package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.db.entity.Reservation;
import com.ssafy.spotlive.db.entity.Timetable;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.repository.ReservationRepository;
import com.ssafy.spotlive.db.repository.TimetableRepository;
import com.ssafy.spotlive.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Transactional
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimetableRepository timetableRepository;

    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();

    @Test
    void insertReservationTest() {
        //given
        String accountEmail = "accountEmail";
        Long timetableId = 1L;
        User user = new User();
        user.setAccountEmail(accountEmail);
        Timetable timetable = new Timetable();
        timetable.setTimetableId(timetableId);

        given(userRepository.findById(accountEmail)).willReturn(Optional.of(user));
        given(timetableRepository.findById(timetableId)).willReturn(Optional.of(timetable));

        //when
        Boolean isSuccess = reservationService.insertReservation(accountEmail, timetableId);
        //then
        assertThat(isSuccess).isTrue();
        verify(userRepository).findById(accountEmail);
        verify(timetableRepository).findById(timetableId);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void deleteReservationByIdTest() {
        //given
        User user = new User();
        String accountEmail = "accountEmail";
        String accessToken = "accessToken";
        user.setAccountEmail(accountEmail);
        user.setAccessToken(accessToken);
        Timetable timetable = new Timetable();
        timetable.setTimetableId(1L);
        doReturn(Optional.of(user)).when(userRepository).findUserByAccessToken(accessToken);
        //when
        Long isSuccess = reservationService.deleteReservationById(accessToken, 1);
        //then
        assertThat(isSuccess).isBetween(0L, 1L);
        verify(userRepository).findUserByAccessToken(anyString());
        verify(reservationRepository).deleteReservationByTimetable_TimetableIdAndUser_AccountEmail(anyLong(), anyString());
    }

    @Test
    void findReservationByIdTest() {
        //given
        long timetableId = 1L;
        String accountEmail = "accountEmail";
        given(reservationRepository.existsByTimetable_TimetableIdAndUser_AccountEmail(timetableId, accountEmail)).willReturn(Boolean.TRUE);
        //when
        Boolean isExist = reservationService.findReservationById(timetableId, accountEmail);
        //then
        assertThat(isExist).isTrue();
        verify(reservationRepository).existsByTimetable_TimetableIdAndUser_AccountEmail(anyLong(), anyString());
    }
}