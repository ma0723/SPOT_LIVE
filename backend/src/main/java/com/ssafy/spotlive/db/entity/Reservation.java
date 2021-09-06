package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(ReservationId.class)
public class Reservation {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountEmail")
    User user;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetableId")
    Timetable timetable;
}
