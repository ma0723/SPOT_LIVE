package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Timetable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long timetableId = null;

    LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showInfoId")
    ShowInfo showInfo;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    List<Video> videoList = new ArrayList<>();
}
